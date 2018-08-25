package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.Game;
import com.hunterstudios.hunters.helper.DateHelper;
import com.hunterstudios.hunters.repository.GameRepository;
import com.hunterstudios.hunters.repository.Period;
import com.hunterstudios.hunters.view.GameView;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameService {

    @NonNull
    private GameRepository gameRepository;

    public List<GameView> getGameList(int year) {
        Period period = DateHelper.createYearPeriod(year);
        List<Game> gameList = gameRepository.selectByPeriod(period);
        //Game game = gameMapper.select(1);
        return new ArrayList<>();
    }

    public int getRecentYear() {

        Game game = gameRepository.selectGame(1);
        System.out.println("game id: " + game.getId());
        System.out.println("game event id: " + game.getEventId());
        System.out.println("event id: " + game.getEvent().getId());
        System.out.println("date: " + game.getEvent().getDate());

        Integer year = gameRepository.getLastYear();
        if (year == null) {
            return OffsetDateTime.now().getYear();
        }
        return year;
    }

    public List<String> getYearList() {
        return gameRepository.getYearList();
    }
}
