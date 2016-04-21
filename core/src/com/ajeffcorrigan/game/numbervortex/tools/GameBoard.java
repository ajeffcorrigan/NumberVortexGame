package com.ajeffcorrigan.game.numbervortex.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.IntArray;

public class GameBoard {

    //Array of GameCell objects.
    private Array<GameCell> gameCells;
    //Starting location of the game board.
    private Vector2 startLoc;
    //Current game level layout.
    private int[][] gameLevel;
    //Texture of the empty game cells.
    private Texture cellTexture;

    public GameBoard(Vector2 sl, int[][] gl, Texture gct) {
        Gdx.app.debug(this.getClass().getSimpleName(),"Start location: "+ sl);
        this.startLoc = sl;
        this.gameLevel = gl;
        this.cellTexture = gct;
        generateGameBoard();
    }

    public void generateGameBoard() {
        gameCells = new Array<GameCell>();
        for(int x = 0; x < gameLevel.length; x++) {
            for(int y = 0; y < gameLevel[x].length; y++) {
                if(gameLevel[x][y] > 0) {
                    gameCells.add(new GameCell(new Vector2(x, y), this.startLoc, true, this.cellTexture));
                } else {
                    gameCells.add(new GameCell(new Vector2(x, y), this.startLoc, false, this.cellTexture));
                }
            }
        }
    }

    public Array<GameCell> getGameCells() {
        return gameCells;
    }

    //Draw the gameboard, both empty cells and filled in cells.
    public void drawBoard(SpriteBatch sb, BitmapFont fnt) {
        float offset;
        sb.begin();
        for(GameCell gc : gameCells) {
            if(gc.isInPlay()) {
                sb.draw(gc.getCellTexture(),gc.getCellPosition().x,gc.getCellPosition().y);
                if(gc.isOccupied()) {
                    offset = (gc.getCellTexture().getWidth() - gc.getGamePiece().getBasePieceTexture().getWidth()) / 2;
                    sb.draw(gc.getGamePiece().getBasePieceTexture(),gc.getGamePiece().getGamePiecePosition().x,gc.getGamePiece().getGamePiecePosition().y);
                    fnt.draw(sb,String.valueOf(gc.getGamePiece().getPieceValue()),gc.getGamePiece().getGamePiecePosition().x + (gc.getGamePiece().getBasePieceTexture().getWidth()*.43f), gc.getGamePiece().getGamePiecePosition().y + (gc.getGamePiece().getBasePieceTexture().getHeight()*.61f));
                }
            }
        }
        sb.end();
    }

    //Draw the cell bound gameboard.
    public void drawBoundBoard(ShapeRenderer sr, Camera gamecam) {
        sr.setProjectionMatrix(gamecam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLACK);
        for (GameCell gc : this.gameCells) {
            if (gc.isInPlay()) {
                sr.circle(gc.getgCellCircle().x, gc.getgCellCircle().y, gc.getgCellCircle().radius);
            }
        }
        sr.end();
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

    //Mark the game cells which are eliminated.
    public void eliminateCells(Vector2 empty) {
        for(GameCell gc : gameCells) {
            if(gc.isInPlay()) {
                if(empty.x == gc.getCellLoc().x) {
                    if(empty.y == (gc.getCellLoc().y-1) || empty.y == (gc.getCellLoc().y+1)) {
                        gc.setSuckedIn(true);
                        Gdx.app.debug(this.getClass().getSimpleName(),"Sucked in cell: "+ gc.getCellLoc());
                        continue;
                    }
                } else if (empty.y == gc.getCellLoc().y) {
                    if(empty.x == (gc.getCellLoc().x - 1) || empty.x == (gc.getCellLoc().x + 1)) {
                        gc.setSuckedIn(true);
                        Gdx.app.debug(this.getClass().getSimpleName(),"Sucked in cell: "+ gc.getCellLoc());
                        continue;
                    }
                } else if ((gc.getCellLoc().x%2 == 0 && empty.y == gc.getCellLoc().y-1) || (gc.getCellLoc().x%2 != 0 && empty.y == gc.getCellLoc().y+1)) {
                    if(empty.x == (gc.getCellLoc().x - 1) || empty.x == (gc.getCellLoc().x + 1)) {
                        gc.setSuckedIn(true);
                        Gdx.app.debug(this.getClass().getSimpleName(),"Sucked in cell: "+ gc.getCellLoc());
                        continue;
                    }
                }
            }
        }

    }

    //Return the score for the specified player.
    public int getScore(int playerNum) {
        int playerScore = 0;
        for(GameCell gc : gameCells) {
            if(gc.isOccupied() && gc.isInPlay() && gc.getGamePiece().getOwnerOfPiece() == playerNum && !gc.isSuckedIn()) {
                playerScore = playerScore + gc.getGamePiece().getPieceValue();
            }
        }
        return playerScore;
    }

}
