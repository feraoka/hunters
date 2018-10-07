package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.*;
import com.hunterstudios.hunters.helper.DateHelper;
import com.hunterstudios.hunters.repository.BatterRepository;
import com.hunterstudios.hunters.repository.BattingRepository;
import com.hunterstudios.hunters.repository.GameRepository;
import com.hunterstudios.hunters.repository.Period;
import com.hunterstudios.hunters.view.BattingEditView;
import com.hunterstudios.hunters.view.BattingSummaryView;
import com.hunterstudios.hunters.view.TitleView;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BattingService {

    @NonNull
    private BattingRepository battingRepository;

    @NonNull
    private BatterRepository batterRepository;

    @NonNull
    private GameRepository gameRepository;

    private static final Map<String, Comparator<BattingSummary>> map = new HashMap<String, Comparator<BattingSummary>>() {{
        put("average", Comparator.comparing(BattingSummary::getAverage).reversed());
        put("rbi", Comparator.comparing(BattingSummary::getRbi).reversed());
    }};

    public BattingSummaryView getBattingSummary(int year) {
        Period period = DateHelper.createYearPeriod(year);
        List<BattingSummary> summary = battingRepository.getBattingSummary(period);
        for (BattingSummary e : summary) {
            e.calculate();
        }
        summary.sort(map.get("average").thenComparing(BattingSummary::getMemberId));
        int requiredNumGames = (gameRepository.getCount(period) + 1) / 2;
        BattingSummaryView view = new BattingSummaryView();
        view.setEffectiveSummary(summary.stream().filter(e -> e.getGame() >= requiredNumGames)
                .collect(Collectors.toList()));
        view.setIneffectiveSummary(summary.stream().filter(e -> e.getGame() < requiredNumGames)
                .collect(Collectors.toList()));
        return view;
    }

    Map<Integer, BattingSummary> getBattingSummaryListOfLastNGames(int n) {
        Map<Integer, BattingSummary> map = new HashMap<>();
        List<BattingSummary> summary = battingRepository.getBattingSummaryOfLastNGames(n);
        for (BattingSummary e : summary) {
            e.calculate();
            map.put(e.getMemberId(), e);
        }
        return map;
    }

    public BattingSelections getSelections() {
        return new BattingSelections();
    }

    public BattingForm getBattingForm(int eventId, BattingIndex index) {
        BattingForm form = new BattingForm();
        form.setUri(String.format("%d/%s", eventId, index.toUri()));
        Batting batting = selectBatting(eventId, index);
        if (batting != null) {
            form.setResult(batting.getResult());
            form.setDirection(batting.getDirection());
            form.setRbi(batting.getRbi());
            form.setPoint(batting.getPoint() > 0);
            form.setSteal(batting.getSteal());
            form.setNext(getNextBattingUri(index));
        }
        form.setPrev(getPrevBattingUri(index));
        return form;
    }

    private String getNextBattingUri(BattingIndex index) {
        BattingIndex next = new BattingIndex(index);
        next.setNumber(next.getNumber() + 1);
        return next.toUri();
    }

    private String getPrevBattingUri(BattingIndex index) {
        if (index.getNumber() == 0) {
            return null;
        }
        BattingIndex next = new BattingIndex(index);
        next.setNumber(next.getNumber() - 1);
        return next.toUri();
    }

    public void updateBatting(int eventId, BattingIndex index, BattingForm form) {
        Batting batting = selectBatting(eventId, index);
        if (batting == null) {
            batting = new Batting();
        }
        batting.setBatterId(getBatterId(eventId, index.getOrder()));
        batting.setInning(index.getInning());
        batting.setNumber(index.getNumber());
        batting.setResult(form.getResult());
        batting.setDirection(form.getDirection());
        batting.setRbi(form.getRbi());
        batting.setPoint(form.isPoint() ? 1 : 0);
        batting.validate();

        if (batting.getId() == 0) {
            battingRepository.insert(batting);
        } else {
            battingRepository.update(batting);
        }
    }

    private int getBatterId(int eventId, int order) {
        BatterSearch search = new BatterSearch(eventId, order);
        Batter batter = batterRepository.findByOrder(search);
        return batter.getId();
    }

    private Batting selectBatting(int eventId, BattingIndex index) {
        BattingSearch searchKey = new BattingSearch();
        searchKey.setEventId(eventId);
        searchKey.setInning(index.getInning());
        searchKey.setOrder(index.getOrder());
        searchKey.setNumber(index.getNumber());
        return battingRepository.select(searchKey);
    }

    /**
     * 指定された試合の全打席情報を返す
     * @param eventId event id
     * @return 全打席情報
     */
    public BattingEditView getBattingView(int eventId) {
        List<Batter> batters = batterRepository.getBatters(eventId);
        int lastInning = batters.stream().map(Batter::getBattings).flatMap(List::stream).mapToInt(Batting::getInning).max().orElse(0);
        lastInning = Integer.max(lastInning + 1, 7);
        List<List<List<String>>> all = new ArrayList<>();
        for (Batter batter : batters) {
            List<List<String>> row = new ArrayList<>();
            List<String> name = new ArrayList<>();
            name.add(batter.getMember().getNickname());
            row.add(name);
            for (int i = 0; i < lastInning; i++) {
                final int inning = i + 1;
                List<String> cell = batter.getBattings().stream().filter(b -> b.getInning() == inning)
                        .map(Batting::toString).collect(Collectors.toList());
                if (cell.isEmpty()) {
                    cell.add("___");
                }
                row.add(cell);
            }
            all.add(row);
        }
        BattingEditView view = new BattingEditView();
        view.setEventId(eventId);
        view.setBattings(all);
        view.setInning(lastInning);
        return view;
    }

    public void deleteBatting(int eventId, BattingIndex index) {
        Batting batting = selectBatting(eventId, index);
        if (batting == null) {
            return;
        }
        battingRepository.delete(batting.getId());
    }

    public String getNextUri(int eventId, BattingIndex index) {
        List<Batter> batters = batterRepository.getBatters(eventId);
        if (batters != null) {
            int numBatters = batters.size();
            int next = (index.getOrder() % numBatters) + 1;
            index.setOrder(next);
            BattingSearch search = new BattingSearch();
            search.setEventId(eventId);
            search.setInning(index.getInning());
            List<Batting> battings = battingRepository.selectByInning(search);
            int numOuts = getNumOuts(battings);
            if (numOuts >= 3) {
                index.setInning(index.getInning() + 1);
            }
        }
        return String.format("%d/%s", eventId, index.toUri());
    }

    private int getNumOuts(List<Batting> battings) {
        return (int)battings.stream().filter(Batting::isOut).count();
    }

    public List<TitleView> getTitleList() {
        List<TitleView> viewList = new ArrayList<>();
        List<Integer> yearList = gameRepository.getYearList();
        Collections.reverse(yearList);
        Comparator<BattingSummary> homerunComparator = Comparator.comparing(BattingSummary::getHomerun);
        Comparator<BattingSummary> rbiComparator = Comparator.comparing(BattingSummary::getRbi);
        Comparator<BattingSummary> averageComparator = Comparator.comparing(BattingSummary::getAverage);
        Comparator<BattingSummary> stealComparator = Comparator.comparing(BattingSummary::getSteal);
        yearList.forEach(year -> {
            Period period = DateHelper.createYearPeriod(year);
            int requiredNumGames = (gameRepository.getCount(period) + 1) / 2;
            List<BattingSummary> summary = battingRepository.getBattingSummary(period);
            for (BattingSummary e : summary) {
                e.calculate();
            }
            TitleView view = new TitleView();
            view.setYear(year);

            List<BattingSummary> average = summary.stream().filter(b -> b.getGame() >= requiredNumGames)
                    .filter(b -> b.getAverage() > 0).collect(maxList(averageComparator));
            TitleView.NameValue averageNameValue = new TitleView.NameValue();
            if (average.size() > 0) {
                averageNameValue.setValue(average.get(0).getAverage());
                averageNameValue.setNames(average.stream().map(BattingSummary::getName).collect(Collectors.toList()));
                view.setAverage(averageNameValue);
            }

            List<BattingSummary> homerun = summary.stream().filter(b -> b.getHomerun() > 0).collect(maxList(homerunComparator));
            TitleView.NameValue homerunNameValue = new TitleView.NameValue();
            if (homerun.size() > 0) {
                homerunNameValue.setValue(homerun.get(0).getHomerun());
                homerunNameValue.setNames(homerun.stream().map(BattingSummary::getName).collect(Collectors.toList()));
                view.setHomerun(homerunNameValue);
            }

            List<BattingSummary> rbi = summary.stream().filter(b -> b.getRbi() > 0).collect(maxList(rbiComparator));
            TitleView.NameValue rbiNameValue = new TitleView.NameValue();
            if (rbi.size() > 0) {
                rbiNameValue.setValue(rbi.get(0).getRbi());
                rbiNameValue.setNames(rbi.stream().map(BattingSummary::getName).collect(Collectors.toList()));
                view.setRbi(rbiNameValue);
            }

            List<BattingSummary> steal = summary.stream().filter(b -> b.getSteal() > 0).collect(maxList(stealComparator));
            TitleView.NameValue stealNameValue = new TitleView.NameValue();
            if (steal.size() > 0) {
                stealNameValue.setValue(steal.get(0).getSteal());
                stealNameValue.setNames(steal.stream().map(BattingSummary::getName).collect(Collectors.toList()));
                view.setSteal(stealNameValue);
            }

            viewList.add(view);
        });
        System.out.println(viewList);
        return viewList;
    }

    private static <T> Collector<T,?,List<T>> maxList(Comparator<? super T> comp) {
        return Collector.of(
                ArrayList::new,
                (list, t) -> {
                    int c;
                    if (list.isEmpty() || (c = comp.compare(t, list.get(0))) == 0) {
                        list.add(t);
                    } else if (c > 0) {
                        list.clear();
                        list.add(t);
                    }
                },
                (list1, list2) -> {
                    if (list1.isEmpty()) {
                        return list2;
                    }
                    if (list2.isEmpty()) {
                        return list1;
                    }
                    int r = comp.compare(list1.get(0), list2.get(0));
                    if (r < 0) {
                        return list2;
                    } else if (r > 0) {
                        return list1;
                    } else {
                        list1.addAll(list2);
                        return list1;
                    }
                });
    }
}
