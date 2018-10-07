package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.service.GameService;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class GameController {

    @NonNull
    private GameService gameService;

    @GetMapping("/games")
    public String getGameList(@RequestParam(name = "year", required = false) String requestedYear, Model model) {
        int year = requestedYear == null ? gameService.getRecentYear()
                : requestedYear.equals("All") ? 0 : Integer.valueOf(requestedYear);
        String yearString = year == 0 ? "All" : String.valueOf(year);
        List<String> yearList = gameService.getYearList();
        model.addAttribute("yearList", yearList);
        model.addAttribute("year", yearString);
        model.addAttribute("summary", gameService.getSummary(year));
        return "game_list";
    }
}
