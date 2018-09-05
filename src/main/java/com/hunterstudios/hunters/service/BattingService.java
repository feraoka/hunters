package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.BattingSummary;
import com.hunterstudios.hunters.helper.DateHelper;
import com.hunterstudios.hunters.repository.BattingRepository;
import com.hunterstudios.hunters.repository.GameRepository;
import com.hunterstudios.hunters.repository.Period;
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
    private GameRepository gameRepository;

    private static final Map<String, Comparator<BattingSummary>> map = new HashMap<String, Comparator<BattingSummary>>() {{
        put("average", Comparator.comparing(BattingSummary::getAverage).reversed());
        put("rbi", Comparator.comparing(BattingSummary::getRbi).reversed());
    }};

    public BattingSummaryView getBattingSummary(int year) {
        Period period = DateHelper.createYearPeriod(year);
        List<BattingSummary> summary = battingRepository.getBattingSummary(period);
        for (BattingSummary e : summary) {
            // 打率
            e.setAverage((float) e.getHit() / e.getDasu());
            // NOTE: 出塁率の分母は 打数+四死球+犠飛 であるが、省略して 打席数 を使用している
            float obp = (float) (e.getHit() + e.getFball() + e.getDball()) / e.getDaseki();
            e.setObp(obp);
            // 長打率
            float slagging = (float) (e.getHit1() + e.getHit2() * 2 + e.getHit3() * 3 + e.getHomerun() * 4) / e.getDasu();
            e.setSlagging(slagging);
            // NOI
            int noi = (int) ((obp + slagging / 3) * 1000);
            e.setNoi(noi);
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
}
