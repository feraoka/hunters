package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.BattingSummary;
import com.hunterstudios.hunters.helper.DateHelper;
import com.hunterstudios.hunters.repository.BattingRepository;
import com.hunterstudios.hunters.repository.GameRepository;
import com.hunterstudios.hunters.repository.Period;
import com.hunterstudios.hunters.view.BattingSummaryView;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}
