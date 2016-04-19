package com.ajeffcorrigan.game.numbervortex.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameBoard {

    private Array<GameCell> gameCells;
    private Vector2 startLoc;
    private int[][] gameLevel;
    private Texture cellTexture;

    public GameBoard(Vector2 sl, int[][] gl) {
        this.startLoc = sl;
        this.gameLevel = gl;
        generateGameBoard();
    }

    public GameBoard(Vector2 sl, int[][] gl,Texture gct) {
        this.startLoc = sl;
        this.gameLevel = gl;
        this.cellTexture = gct;
        generateGameBoard();
    }

    public void generateGameBoard() {
        gameCells = new Array<GameCell>();
        for(int x = 0; x < gameLevel.length; x++) {
            for(int y = 0; y < gameLevel[x].length; y++) {
                if(this.cellTexture != null) {
                    gameCells.add(new GameCell(new Vector2(x, y), this.startLoc));
                } else {
                    gameCells.add(new GameCell(new Vector2(x, y), this.startLoc));
                }
                if(gameLevel[x][y] > 0) { gameCells.peek().setInPlay(true); }
            }
        }
    }

    public Array<GameCell> getGameCells() {
        return gameCells;
    }

    public void drawBoard(ShapeRenderer sr, Camera gameCam) {
        sr.setProjectionMatrix(gameCam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        for(GameCell gc : gameCells) {
            //Gdx.app.debug(this.getClass().getSimpleName(),"gCell x: " + gc.getCellLoc().x +" y:"+ gc.getCellLoc().y);
            //gc.drawCircle(sr);
            gc.drawInPlayBoard(sr);
        }
        sr.end();
    }

    public void drawBoard(SpriteBatch sb) {
        sb.begin();
        for(GameCell gc : gameCells) {
            if(gc.isInPlay) { sb.draw(this.cellTexture,gc.getgCellCircle().x-35,gc.getgCellCircle().y-35); }
        }
        sb.end();
    }

    //Get and set for start location variable; startLoc
    public Vector2 getStartLoc() { return startLoc; }
    public void setStartLoc(Vector2 sl) {
        startLoc = sl;
    }

    //Get and set for game level details.
    public int[][] getGameLevel() { return gameLevel; }
    public void setGameLevel(int[][] gameLevel) {
        this.gameLevel = gameLevel;
        this.generateGameBoard();
    }

    public Array<GameCell> suckedIn(Vector2 empty) {
        Array<GameCell> vals = new Array<GameCell>();
        for(GameCell gc : gameCells) {
            if(empty.x == gc.getCellLoc().x && empty.y == (gc.getCellLoc().y-1)) { vals.add(gc); }
            if(empty.x == gc.getCellLoc().x && empty.y == (gc.getCellLoc().y+1)) { vals.add(gc); }
            if(empty.x == (gc.getCellLoc().x - 1) && empty.y == gc.getCellLoc().y) { vals.add(gc);}
            if(empty.x == (gc.getCellLoc().x + 1) && empty.y == gc.getCellLoc().y) { vals.add(gc);}
            if(gc.getCellLoc().x%2 == 0) {
                if(empty.x == (gc.getCellLoc().x - 1) && empty.y == gc.getCellLoc().y-1) { vals.add(gc);}
                if(empty.x == (gc.getCellLoc().x + 1) && empty.y == gc.getCellLoc().y-1) { vals.add(gc);}
            } else {
                if(empty.x == (gc.getCellLoc().x - 1) && empty.y == gc.getCellLoc().y+1) { vals.add(gc);}
                if(empty.x == (gc.getCellLoc().x + 1) && empty.y == gc.getCellLoc().y+1) { vals.add(gc);}
            }
        }
        return vals;
    }
}
