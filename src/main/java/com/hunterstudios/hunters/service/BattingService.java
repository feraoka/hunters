package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.BattingSummary;
import com.hunterstudios.hunters.entity.Member;
import com.hunterstudios.hunters.helper.DateHelper;
import com.hunterstudios.hunters.repository.BattingRepository;
import com.hunterstudios.hunters.repository.MemberRepository;
import com.hunterstudios.hunters.repository.Period;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BattingService {

    @NonNull
    private BattingRepository battingRepository;

    public List<BattingSummary> getBattingSummary(int year) {
        Period period = DateHelper.createYearPeriod(year);
        List<BattingSummary> summary = battingRepository.getBattingSummary(period);
        for (BattingSummary e : summary) {
            // 打率
            e.setAverage((float) e.getHit() / e.getDasu());
            // NOTE: 出塁率の分母は 打数+四死球+犠飛 であるが、省略して 打席数 を使用している
            float obp = (float)(e.getHit() + e.getFball() + e.getDball()) / e.getDaseki();
            e.setObp(obp);
            // 長打率
            float slagging = (float)(e.getHit1() + e.getHit2() * 2 + e.getHit3() * 3 + e.getHomerun() * 4) / e.getDasu();
            e.setSlagging(slagging);
            // NOI
            int noi = (int)((obp + slagging / 3) * 1000);
            e.setNoi(noi);
        }
        return summary;
    }
}
