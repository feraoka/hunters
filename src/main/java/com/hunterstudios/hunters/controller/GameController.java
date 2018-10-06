package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.service.GameService;
import com.hunterstudios.hunters.view.GameView;
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
    public String getGameList(@RequestParam(name = "year", required = false) Integer requestedYear, Model model) {
        List<String> yearList = gameService.getYearList();
        model.addAttribute("yearList", yearList);
        int year = requestedYear == null ? gameService.getRecentYear() : requestedYear;
        model.addAttribute("year", year);

        model.addAttribute("summary", gameService.getSummary(year));
        return "game_list";
    }
}
