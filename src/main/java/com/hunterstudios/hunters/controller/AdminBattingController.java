package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.entity.BattingForm;
import com.hunterstudios.hunters.entity.BattingIndex;
import com.hunterstudios.hunters.exception.InvalidDataException;
import com.hunterstudios.hunters.service.BattingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
                                 @PathVariable(name = "number") Integer number,
                                 Model model) {
        BattingIndex index = new BattingIndex(order, inning, number);
        model.addAttribute("battings", battingService.getBattingView(eventId));
        model.addAttribute("selected", index);
        model.addAttribute("form", battingService.getBattingForm(eventId, index));
        model.addAttribute("selections", battingService.getSelections());
        return "edit_batting";
    }

    @PostMapping({"{id}/{order}/{inning}", "{id}/{order}/{inning}/{number}"})
    public String updateBatting(@PathVariable(name = "id") Integer eventId,
                                @PathVariable(name = "order") Integer order,
                                @PathVariable(name = "inning") Integer inning,
                                @PathVariable(name = "number") Integer number,
                                @ModelAttribute("form") BattingForm form, BindingResult result,
                                Model model) {
        BattingIndex index = new BattingIndex(order, inning, number);
        try {
            if (form.isDelete()) {
                battingService.deleteBatting(eventId, index);
            } else {
                battingService.updateBatting(eventId, index, form);
            }
        } catch (InvalidDataException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("battings", battingService.getBattingView(eventId));
            model.addAttribute("selected", index);
            model.addAttribute("form", battingService.getBattingForm(eventId, index));
            model.addAttribute("selections", battingService.getSelections());
            return "edit_batting";
        }
        String uri = battingService.getNextUri(eventId, index);
        return "redirect:/admin/battings/" + uri;
    }

}
