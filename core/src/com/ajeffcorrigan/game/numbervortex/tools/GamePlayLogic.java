package com.ajeffcorrigan.game.numbervortex.tools;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.HashMap;

public class GamePlayLogic {

    public GamePlayLogic() {}



    public int getWinner(Array<GamePlayer> gps) {
        int currentWinCode = 0;
        int currentWinPlayer = 0;
        int currentHighScore = 0;
        boolean firstPlayer = true;
        for(GamePlayer gp : gps) {
            if(firstPlayer) {
                currentHighScore = gp.getScore();
                currentWinPlayer = gp.getPlayerNum();
                firstPlayer = false;
                continue;
            }
            if(gp.getScore() > currentHighScore) {
                currentHighScore = gp.getScore();
                currentWinPlayer = gp.getPlayerNum();
                currentWinCode = gp.getPlayerNum();
                continue;
            } else if (gp.getScore() == currentHighScore) {
                currentWinCode = 99;
                continue;
            } else if (gp.getScore() < currentHighScore) {
                currentWinCode = gp.getPlayerNum();
                continue;
            }
        }
        return currentWinCode;
    }

}
