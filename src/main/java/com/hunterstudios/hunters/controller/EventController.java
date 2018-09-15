package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.entity.EventForm;
import com.hunterstudios.hunters.service.AdminEventService;
import com.hunterstudios.hunters.service.EventService;
import com.hunterstudios.hunters.view.EventDetailView;
import com.hunterstudios.hunters.view.EventView;
import java.util.List;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class EventController {

    @NonNull
    private EventService eventService;

    @NonNull
    private AdminEventService adminEventService;
    private ObjectError error;

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

    private String editEvent(EventForm form, Model model) {
        model.addAttribute("members", adminEventService.getMemberList());
        model.addAttribute("form", form);
        return "create_event";
    }

    @GetMapping("/admin/events/add")
    public String getCreateForm(Model model) {
        EventForm form = adminEventService.createEventForm(null);
        return editEvent(form, model);
    }

    @PostMapping("/admin/events/add")
    public String addEvent(@ModelAttribute("form") @Valid EventForm form,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            return editEvent(form, model);
        }
        adminEventService.upsertEvent(form);
        return "redirect:/admin/events/" + form.getId();
    }

    @GetMapping("/admin/events/{id}")
    public String getEventForm(@PathVariable(name = "id") Integer id, Model model) {
        EventForm form = adminEventService.createEventForm(id);
        return editEvent(form, model);
    }
}
