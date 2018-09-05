package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.service.BattingService;
import com.hunterstudios.hunters.service.EventService;
import com.hunterstudios.hunters.view.BattingSummaryView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class BattingController {

    @NonNull
    private EventService eventService;

    @NonNull
    private BattingService battingService;

    @GetMapping("/battings")
    public String getBattingList(@RequestParam(name = "year", required = false) Integer requestedYear, Model model) {
        int year = requestedYear == null ? eventService.getRecentYear() : requestedYear;
        BattingSummaryView summary = battingService.getBattingSummary(year);
        model.addAttribute("summary", summary.getEffectiveSummary());
        model.addAttribute("ineffective", summary.getIneffectiveSummary());
        model.addAttribute("ineffectiveCount", summary.getIneffectiveSummary().size());
        return "batting_list";
    }
}
