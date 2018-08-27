package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.Batter;
import com.hunterstudios.hunters.entity.Batting;
import com.hunterstudios.hunters.entity.Event;
import com.hunterstudios.hunters.entity.Game;
import com.hunterstudios.hunters.helper.DateHelper;
import com.hunterstudios.hunters.repository.EventRepository;
import com.hunterstudios.hunters.repository.Period;
import com.hunterstudios.hunters.view.EventDetailView;
import com.hunterstudios.hunters.view.EventView;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventService {

    @NonNull
    private EventRepository eventRepository;

    public List<EventView> getEventList(int year) {
        Period period = DateHelper.createYearPeriod(year);
        List<Event> eventList = eventRepository.selectByPeriod(period);
        return eventList.stream().map(EventView::new).collect(Collectors.toList());
    }

    public List<String> getYearList() {
        return eventRepository.getYearList();
    }

    public int getRecentYear() {
        Integer year = eventRepository.getLastYear();
        if (year == null) {
            return OffsetDateTime.now().getYear();
        }
        return year;
    }

    /**
     * create event detail view
     * TODO unescape html coded characters in event note, and replace return code with <br/>
     *
     * @param id event ID
     * @return EventDetailView object
     */
    public EventDetailView getDetails(int id) {
        Event event = eventRepository.getGameAndBattings(id);
        EventDetailView view = new EventDetailView(event);
        if (event.getGame() != null) {
            view.setScoreboard(makeScore(event));
            view.setBatting(makeBatting(event));
        }
        return view;
    }

    private List<List<String>> makeScore(Event event) {
        Game game = event.getGame();
        List<List<String>> list = new ArrayList<>();
        List<String> scoreA = makeScore(game.getScoreA(), game.isBatFirst() ? "Hunters" : event.getOpponent());
        List<String> scoreB = makeScore(game.getScoreB(), game.isBatFirst() ? event.getOpponent() : "Hunters");
        List<String> top = new ArrayList<>();
        top.add("Team");
        for (int i = 0; i < scoreA.size() - 2; i++) {
            top.add(String.valueOf(i + 1));
        }
        top.add("Total");
        list.add(top);
        list.add(scoreA);
        list.add(scoreB);
        return list;
    }

    private List<String> makeScore(String rawScore, String name) {
        List<String> list = new ArrayList<>();
        list.add(name);
        List<String> points = Arrays.asList(rawScore.split(","));
        list.addAll(points);
        int total = points.stream().mapToInt(Integer::parseInt).sum();
        list.add(String.valueOf(total));
        return list;
    }

    private List<List<String>> makeBatting(Event event) {
        List<Batter> batters = event.getBatters();
        int innings = batters.stream().map(Batter::getBattings).flatMap(Collection::stream)
                .map(Batting::getInning).max(Comparator.naturalOrder()).orElse(0);
        List<List<String>> battingTable = new ArrayList<>();
        List<String> header = new ArrayList<>();
        header.add("打者");
        header.addAll(IntStream.rangeClosed(1, innings).mapToObj(Integer::toString).collect(Collectors.toList()));
        battingTable.add(header);
        batters.forEach(batter -> {
            List<String> line = new ArrayList<>();
            line.add(batter.getMember().getNickname());

            IntStream.rangeClosed(1, innings).forEach(inning -> {
                List<Batting> battings = batter.getBattings(inning);
                String result = battings.stream().map(Batting::toString).collect(Collectors.joining());
                line.add(result);
            });
            battingTable.add(line);
        });
        return battingTable;
    }
}
