package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.service.EventService;
import com.hunterstudios.hunters.view.EventDetailView;
import com.hunterstudios.hunters.view.EventView;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class EventController {

    @NonNull
    private EventService eventService;

    @GetMapping("/events")
    public String getEventList(@RequestParam(name = "year", required = false) Integer requestedYear, Model model) {
        List<String> yearList = eventService.getYearList();
        model.addAttribute("yearList", yearList);
        int year = requestedYear == null ? eventService.getRecentYear() : requestedYear;
        model.addAttribute("year", year);
        List<EventView> eventList = eventService.getEventList(year);
        model.addAttribute("eventList", eventList);
        return "event_list";
    }

    @GetMapping("/events/{id}")
    public String getEvent(@PathVariable(name = "id") Integer id, Model model) {
        EventDetailView view = eventService.getDetails(id);
        model.addAttribute("event", view);
        return "event_detail";
    }

}
