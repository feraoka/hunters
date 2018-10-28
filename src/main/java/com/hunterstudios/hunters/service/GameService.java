package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.Game;
import com.hunterstudios.hunters.entity.ScoreboardForm;
import com.hunterstudios.hunters.helper.DateHelper;
import com.hunterstudios.hunters.repository.GameRepository;
import com.hunterstudios.hunters.repository.Period;
import com.hunterstudios.hunters.view.GameSummaryView;
import com.hunterstudios.hunters.view.GameView;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@RequiredArgsConstructor
@Service
public class GameService {

    @NonNull
    private GameRepository gameRepository;

    private static final List<String> TEAMS = Collections.unmodifiableList(new ArrayList<String>() {{
        add("ハンターズ");
        add("対戦相手");
    }});

    public GameSummaryView getSummary(int year) {
        GameSummaryView view = new GameSummaryView();
        Period period = DateHelper.createYearPeriod(year);
        List<Game> list = gameRepository.selectByPeriod(period);
        view.setNumGames(list.size());
        view.setNumWins((int)list.stream().filter(g -> g.getResult() > 0).count());
        view.setNumLoses((int)list.stream().filter(g -> g.getResult() < 0).count());
        view.setNumDraws((int)list.stream().filter(g -> g.getResult() == 0).count());
        view.setGames(list.stream().map(GameView::new).collect(Collectors.toList()));
        return view;
    }

    public int getRecentYear() {
        Integer year = gameRepository.getLastYear();
        if (year == null) {
            return OffsetDateTime.now().getYear();
        }
        return year;
    }

    public List<String> getYearList() {
        List<String> list = gameRepository.getYearList().stream().map(String::valueOf).collect(Collectors.toList());
        list.add("All");
        return list;
    }

    public ScoreboardForm getScoreboardForm(int eventId) {
        ScoreboardForm form = new ScoreboardForm();
        form.setEventId(eventId);
        form.setTeams(TEAMS);
        Game game = gameRepository.getGame(eventId);
        if (game == null) {
            form.setTeamA(TEAMS.get(0));
            form.setTeamB(TEAMS.get(1));
            form.setScoreA(IntStream.rangeClosed(1, 7).mapToObj(i -> "").collect(Collectors.toList()));
            form.setScoreB(IntStream.rangeClosed(1, 7).mapToObj(i -> "").collect(Collectors.toList()));
        } else {
            form.setTeamA(game.isBatFirst() ? TEAMS.get(0) : TEAMS.get(1));
            form.setTeamB(game.isBatFirst() ? TEAMS.get(1) : TEAMS.get(0));
            List<String> scoreA = new ArrayList<>(Arrays.asList(game.getScoreA().split(",")));
            List<String> scoreB = new ArrayList<>(Arrays.asList(game.getScoreB().split(",")));
            while (scoreA.size() > scoreB.size()) {
                scoreB.add("");
            }
            while (scoreA.size() < scoreB.size()) {
                scoreA.add("");
            }
            for (int i = 0; i < 3; i++) { //イニング増加に備えてちょびっと追加
                scoreA.add("");
                scoreB.add("");
            }
            form.setScoreA(scoreA);
            form.setScoreB(scoreB);
        }
        return form;
    }

    public void updateScoreboard(ScoreboardForm form) {
        Game game = new Game();
        game.setEventId(form.getEventId());
        int lastInning = getLastInning(form.getScoreA(), form.getScoreB());
        List<String> trimmedScoreA = trimScore(form.getScoreA(), lastInning);
        List<String> trimmedScoreB = trimScore(form.getScoreB(), lastInning);
        String scoreA = StringUtils.join(trimmedScoreA, ',');
        String scoreB = StringUtils.join(trimmedScoreB, ',');
        int totalA = trimmedScoreA.stream().filter(s -> s.matches("[0-9]+")).mapToInt(Integer::parseInt).sum();
        int totalB = trimmedScoreB.stream().filter(s -> s.matches("[0-9]+")).mapToInt(Integer::parseInt).sum();
        boolean batFirst = form.getTeamA().equals(TEAMS.get(0));
        int pointGot = batFirst ? totalA : totalB;
        int pointLost = batFirst ? totalB : totalA;
        game.setScoreA(scoreA);
        game.setScoreB(scoreB);
        game.setPointGot(pointGot);
        game.setPointLost(pointLost);
        game.setResult(Integer.compare(pointGot, pointLost));
        game.setBatFirst(batFirst);
        Game record = gameRepository.getGame(form.getEventId());
        if (record == null) {
            gameRepository.insert(game);
        } else {
            gameRepository.update(game);
        }
    }

    private int getLastInning(List<String> scoreA, List<String> scoreB) {
        int lastInning = 0;
        for (int i = 0; i < scoreA.size(); i++) {
            if (!StringUtils.isEmpty(scoreA.get(i))) {
                lastInning = i;
            }
        }
        for (int i = lastInning; i < scoreB.size(); i++) {
            if (!StringUtils.isEmpty(scoreB.get(i))) {
                lastInning = i;
            }
        }
        return lastInning;
    }

    private List<String> trimScore(List<String> score, int lastInning) {
        List<String> trimmed = new ArrayList<>();
        for (int i = 0; i <= lastInning; i++) {
            if (StringUtils.isEmpty(score.get(i))) {
                trimmed.add("0");
            } else {
                trimmed.add(score.get(i));
            }
        }
        return trimmed;
    }
}
