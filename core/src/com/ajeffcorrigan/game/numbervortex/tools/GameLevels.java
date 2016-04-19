package com.ajeffcorrigan.game.numbervortex.tools;

import com.badlogic.gdx.utils.Array;

public class GameLevels {

    private Array<int[][]> levelDetails;

    private int[][] gameLevel1 = {
            {0,1,1,1,1,1,1,0},
            {0,1,1,1,1,1,0,0},
            {0,0,1,1,1,1,0,0},
            {0,0,1,1,1,0,0,0},
            {0,0,0,1,1,0,0,0},
            {0,0,0,1,0,0,0,0}
    };

    private int[][] gameLevel2 = {
            {0,1,1,1,0},
            {0,1,1,0,0},
            {0,0,1,0,0}
    };

    public GameLevels() {
        levelDetails = new Array<int[][]>();
        levelDetails.add(gameLevel1);
        levelDetails.add(gameLevel2);
    }

    public int[][] getLevelDetails(int index) {
        return levelDetails.get(index);
    }
    public int[][] getFirstLevel() { return this.levelDetails.first(); }
}
