package com.hunterstudios.hunters.entity;

import lombok.Data;

@Data
public class GameTeam {
    private String team;
    private int numMatches;
    private int numWins;
    private int numLoses;
    private int numDraws;
    private float rate;
}
