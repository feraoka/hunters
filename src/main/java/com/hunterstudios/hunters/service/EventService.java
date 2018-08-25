package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.Event;
import com.hunterstudios.hunters.helper.DateHelper;
import com.hunterstudios.hunters.repository.EventRepository;
import com.hunterstudios.hunters.repository.Period;
import com.hunterstudios.hunters.view.EventView;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
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
        Function<Event, EventView> eventToView = (e) -> {
            EventView view = new EventView();
            view.setId(e.getId());
            view.setDate(DateHelper.toDate(e.getDate()));
            view.setType(e.getType());
            view.setLocation(e.getLocation());
            view.setOpponent(e.getOpponent());
            return view;
        };
        return eventList.stream().map(eventToView).collect(Collectors.toList());
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
}
