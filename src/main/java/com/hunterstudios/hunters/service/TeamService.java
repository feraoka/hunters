package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.Game;
import com.hunterstudios.hunters.entity.GameTeam;
import com.hunterstudios.hunters.repository.GameRepository;
import com.hunterstudios.hunters.view.GameSummaryView;
import com.hunterstudios.hunters.view.GameView;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamService {

    @NonNull
    private GameRepository gameRepository;

    public List<GameTeam> getTeamList() {
        List<GameTeam> list = gameRepository.getSummaryByTeam();
        list.forEach(g -> {
            float rate = (float)g.getNumWins() / g.getNumMatches();
            g.setRate(rate);
        });
        return list;
    }

    public GameSummaryView getTeam(String name) {
        GameSummaryView view = new GameSummaryView();
        List<Game> list = gameRepository.selectByTeam(name);
        view.setNumGames(list.size());
        view.setNumWins((int)list.stream().filter(g -> g.getResult() > 0).count());
        view.setNumLoses((int)list.stream().filter(g -> g.getResult() < 0).count());
        view.setNumDraws((int)list.stream().filter(g -> g.getResult() == 0).count());
        view.setGames(list.stream().map(GameView::new).collect(Collectors.toList()));
        return view;
    }
}
