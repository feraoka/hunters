package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.Batter;
import com.hunterstudios.hunters.repository.BattingRepository;
import com.hunterstudios.hunters.view.BattingGameView;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BattingService {

    @NonNull
    private BattingRepository battingRepository;

    public BattingGameView getGame(int eventId) {
        int numInnings = Optional.ofNullable(battingRepository.getNumInnings(eventId)).orElse(0);
        System.out.println("# of innings=" + numInnings);
        List<BattingGameView.MemberBatting> list = new ArrayList<>();
        List<Batter> batterList = battingRepository.getBatterList(eventId);
        batterList.forEach((b) -> {
            BattingGameView.MemberBatting batting = new BattingGameView.MemberBatting();
            batting.setName(b.getMember().getNickname());
            System.out.println("batter id: " + b.getId() + ", name: " + b.getMember().getNickname());
            list.add(batting);
        });
        BattingGameView view = new BattingGameView();
        view.setData(list);
        return view;
    }
}
