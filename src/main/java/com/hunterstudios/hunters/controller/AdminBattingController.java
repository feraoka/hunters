package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.entity.BattingForm;
import com.hunterstudios.hunters.service.BattingService;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/battings")
public class AdminBattingController {

    @NonNull
    private BattingService battingService;

    /**
     * edit batting
     * @param eventId event id
     * @param order   batting order. starts from 1
     * @param inning  inning of bat. starts from 1
     * @param number  batting number. starts from 0 (default)
     * @param model   model for UI
     * @return batting form of selected batting
     */
    @GetMapping({"{id}/{order}/{inning}", "{id}/{order}/{inning}/{number}"})
    public String getBattingForm(@PathVariable(name = "id") Integer eventId,
                                 @PathVariable(name = "order") Integer order,
                                 @PathVariable(name = "inning") Integer inning,
                                 @PathVariable(name = "number", required = false) Integer number,
                                 Model model) {
        int n = number == null ? 0 : number;
        model.addAttribute("battings", battingService.getBattingView(eventId));
        model.addAttribute("form", battingService.getBattingForm(eventId, order, inning, n));
        model.addAttribute("selections", battingService.getSelections());
        return "edit_batting";
    }

    @PostMapping({"{id}/{order}/{inning}", "{id}/{order}/{inning}/{number}"})
    public String updateBatting(@PathVariable(name = "id") Integer eventId,
                                @PathVariable(name = "order") Integer order,
                                @PathVariable(name = "inning") Integer inning,
                                @PathVariable(name = "number", required = false) Integer number,
                                @ModelAttribute("form") @Valid BattingForm form,
                                Model model) {
        int n = number == null ? 0 : number;
        //battingService.updateBatting(eventId, order, inning, n, form);
        return "redirect:/admin/battings/{id}/{order}/{inning}/{number}";
    }
}
