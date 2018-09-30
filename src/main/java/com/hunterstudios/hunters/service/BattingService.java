package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.*;
import com.hunterstudios.hunters.helper.DateHelper;
import com.hunterstudios.hunters.repository.BatterRepository;
import com.hunterstudios.hunters.repository.BattingRepository;
import com.hunterstudios.hunters.repository.GameRepository;
import com.hunterstudios.hunters.repository.Period;
import com.hunterstudios.hunters.view.BattingEditView;
import com.hunterstudios.hunters.view.BattingSummaryView;
import java.util.*;
import java.util.stream.Collectors;
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
        int requiredNumGames = gameRepository.getCount(period) / 2;
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
}
