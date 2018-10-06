package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.entity.GameTeam;
import com.hunterstudios.hunters.service.TeamService;
import com.hunterstudios.hunters.view.GameSummaryView;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class TeamController {

    @NonNull
    private TeamService teamService;

    @GetMapping("/teams")
    public String getTeamList(Model model) {
        List<GameTeam> list = teamService.getTeamList();
        model.addAttribute("teams", list);
        return "team_list";
    }
    @GetMapping("/teams/{name}")
    public String getTeam(@PathVariable(name = "name") String name,
                          Model model) {
        GameSummaryView view = teamService.getTeam(name);
        model.addAttribute("summary", view);
        model.addAttribute("name", name);
        return "team";
    }
}
