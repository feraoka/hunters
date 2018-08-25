package com.hunterstudios.hunters.view;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class GameView {
    private int id;
    private Date date;
    private String type;
    private String location;
    private String result;
    private String opponent;
    private List<String> teamA;
    private List<String> teamB;
    private String totalScoreA;
    private String totalScoreB;
}
