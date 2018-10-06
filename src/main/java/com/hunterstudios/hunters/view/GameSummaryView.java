package com.hunterstudios.hunters.view;

import java.util.List;
import lombok.Data;

@Data
public class GameSummaryView {
    private int numGames;
    private int numWons;
    private int numLosts;
    private int numDrews;
    private List<GameView> games;

}
