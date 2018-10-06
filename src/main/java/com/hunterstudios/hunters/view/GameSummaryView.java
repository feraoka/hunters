package com.hunterstudios.hunters.view;

import java.util.List;
import lombok.Data;

@Data
public class GameSummaryView {
    private int numGames;
    private int numWins;
    private int numLoses;
    private int numDraws;
    private List<GameView> games;

}
