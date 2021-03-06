package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.service.BattingService;
import com.hunterstudios.hunters.service.EventService;
import com.hunterstudios.hunters.service.GameService;
import com.hunterstudios.hunters.view.BattingSummaryView;
import com.hunterstudios.hunters.view.TitleView;
import java.util.List;
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
    private BattingService battingService;

    @NonNull
    private GameService gameService;

    @GetMapping("/battings")
    public String getBattingList(@RequestParam(name = "year", required = false) String requestedYear, Model model) {
        int year = requestedYear == null ? gameService.getRecentYear()
                : requestedYear.equals("All") ? 0 : Integer.valueOf(requestedYear);
        String yearString = year == 0 ? "All" : String.valueOf(year);
        model.addAttribute("year", yearString);
        List<String> yearList = gameService.getYearList();
        model.addAttribute("yearList", yearList);
        BattingSummaryView summary = battingService.getBattingSummary(year);
        model.addAttribute("summary", summary.getEffectiveSummary());
        model.addAttribute("ineffective", summary.getIneffectiveSummary());
        model.addAttribute("ineffectiveCount", summary.getIneffectiveSummary().size());
        model.addAttribute("total", summary.getTotal());
        return "batting_list";
    }

    @GetMapping("/battings/titles")
    public String getBattingTitles(Model model) {
        List<TitleView> view = battingService.getTitleList();
        model.addAttribute("titles", view);
        return "batting_title_list";
    }
}
