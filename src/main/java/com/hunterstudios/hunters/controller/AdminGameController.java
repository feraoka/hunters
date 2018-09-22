package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.entity.BatterForm;
import com.hunterstudios.hunters.service.AttendeeService;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class AdminGameController {

    @NonNull
    private AttendeeService attendeeService;

    @GetMapping("/admin/games/{id}/members")
    public String getMemberForm(@PathVariable(name = "id") Integer id, Model model) {
        model.addAttribute("form", attendeeService.getBatterForm(id));
        model.addAttribute("members", attendeeService.getAttendeeList(id));
        return "edit_game_member";
    }

    @PostMapping("/admin/games/{id}/members")
    public String updateMemberList(@PathVariable(name = "id") Integer id, @ModelAttribute("form") @Valid BatterForm form,
                                   BindingResult result, Model model) {
        model.addAttribute("members", attendeeService.getAttendeeList(id));
        if (result.hasErrors()) {
            return "edit_game_member";
        }
        model.addAttribute("form", attendeeService.updateMemberList(form));
        return "edit_game_member"; // or back to edit event view
    }
}
