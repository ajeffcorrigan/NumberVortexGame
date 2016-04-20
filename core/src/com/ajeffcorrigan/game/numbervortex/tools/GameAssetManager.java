package com.ajeffcorrigan.game.numbervortex.tools;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by jacorrigan on 4/20/2016.
 */
public class GameAssetManager {
    public GameAssetManager() {
        jAssets.loadTextureAs("cell","cell.png");
        jAssets.loadTextureAs(Color.BLUE,"bluePiece.png");
        jAssets.loadTextureAs(Color.GREEN,"greenPiece.png");
    }
}
