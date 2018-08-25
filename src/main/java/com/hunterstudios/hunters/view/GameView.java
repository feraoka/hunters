package com.hunterstudios.hunters.view;

import com.hunterstudios.hunters.entity.Game;
import com.hunterstudios.hunters.helper.DateHelper;
import java.util.Date;
import lombok.Data;

@Data
public class GameView {
    private Game game;
    private Date date;
    private String result;

    public GameView(Game game) {
        this.game = game;
        this.date = DateHelper.toDate(game.getEvent().getDate());
        if (game.getResult() == 1) {
            this.result = "勝ち";
        } else if (game.getResult() == -1) {
            this.result = "負け";
        } else {
            this.result = "引き分け";
        }
    }
}
