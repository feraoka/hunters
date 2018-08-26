package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.service.BattingService;
import com.hunterstudios.hunters.view.BattingGameView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class BattingController {

    @NonNull
    private BattingService battingService;

    @GetMapping("/battings/{id}")
    public String getBattingsOfGame(@PathVariable(name = "id", required = false) Integer id, Model model) {
        BattingGameView battings = battingService.getGame(id);
        model.addAttribute("battings", battings);
        return "batting_game";
    }
}
