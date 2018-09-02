package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.Game;
import com.hunterstudios.hunters.helper.DateHelper;
import com.hunterstudios.hunters.repository.GameRepository;
import com.hunterstudios.hunters.repository.Period;
import com.hunterstudios.hunters.view.GameView;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
        return gameList.stream().map(GameView::new).collect(Collectors.toList());
    }

    public int getRecentYear() {
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
