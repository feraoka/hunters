package com.hunterstudios.hunters.entity;

import java.util.List;
import lombok.Data;

@Data
public class ScoreboardForm {
    private int eventId;
    private List<String> teams;
    private String teamA;
    private String teamB;
    private List<String> scoreA;
    private List<String> scoreB;
}
