package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.entity.EventForm;
import com.hunterstudios.hunters.service.AdminEventService;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class AdminEventController {

    @NonNull
    private AdminEventService adminEventService;

    private String editEvent(EventForm form, Model model) {
        model.addAttribute("members", adminEventService.getMemberList());
        model.addAttribute("form", form);
        return "edit_event";
    }

    /**
     * イベントの新規追加フォームを返す
     * @param model model for template
     * @return form template
     */
    @GetMapping("/admin/events/add")
    public String getCreateForm(Model model) {
        EventForm form = adminEventService.createEventForm(null);
        return editEvent(form, model);
    }

    /**
     * イベントの新規追加、更新
     * @param form form given by requester
     * @param result binding result
     * @param model model for template
     * @return edit form template
     */
    @PostMapping("/admin/events")
    public String addEvent(@ModelAttribute("form") @Valid EventForm form,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            return editEvent(form, model);
        }
        adminEventService.upsertEvent(form);
        return "redirect:/admin/events/" + form.getId();
    }

    /**
     * イベントの更新フォームを返す
     * @param id event id
     * @param model model for template
     * @return edit form template
     */
    @GetMapping("/admin/events/{id}")
    public String getEventForm(@PathVariable(name = "id") Integer id, Model model) {
        EventForm form = adminEventService.createEventForm(id);
        return editEvent(form, model);
    }

    /**
     * イベントを削除する（未実装）　
     * @param id event id of event to be deleted
     * @param model model for template
     * @return top page (tentative)
     */
    @DeleteMapping("/admin/events/{id}")
    public String deleteEvent(@PathVariable(name = "id") Integer id, Model model) {
        System.out.println("TODO: delete: " + id);
        return "top";
    }
}
