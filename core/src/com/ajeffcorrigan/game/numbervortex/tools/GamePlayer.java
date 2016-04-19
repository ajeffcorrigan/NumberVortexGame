package com.ajeffcorrigan.game.numbervortex.tools;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GamePlayer {

    private static final int piecesToPlay = 10;

    private Array<GamePiece> gamePieces;
    private Color pColor;
    private int playerNum;
    private GamePiece activePiece;
    private int activeTurn = 0;
    private int score = 0;
    private int scoreSucked = 0;
    private Texture baseTexture;
    private Array<Texture> numbers = new Array<Texture>(10);

    public GamePlayer(Vector2 startLoc,Color c,int pn) {
        this.pColor = c;
        this.playerNum = pn;
        gamePieces = new Array<GamePiece>(piecesToPlay);
        for(int x = 1; x <= piecesToPlay; x++) { gamePieces.add(new GamePiece(x,startLoc)); }
    }

    public GamePlayer(Vector2 startLoc,Texture baseText, int pn) {
        this.playerNum = pn;
        gamePieces = new Array<GamePiece>(piecesToPlay);
        this.baseTexture = baseText;
        for(int x = 1; x <= piecesToPlay; x++) { gamePieces.add(new GamePiece(x,startLoc)); }
    }

    public Array<GamePiece> getGamePieces() { return gamePieces; }
    public GamePiece popGamePiece() { return gamePieces.pop(); }

    public void drawPieces(ShapeRenderer sr, Camera gameCam) {
        sr.setProjectionMatrix(gameCam.combined);

        if (anyActiveMoves()) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(pColor);
            sr.circle(activePiece.getgPieceCircle().x,activePiece.getgPieceCircle().y,activePiece.getgPieceCircle().radius);
            sr.end();
        }
        sr.begin(ShapeRenderer.ShapeType.Line);
        for(GamePiece gp : gamePieces) {
            sr.setColor(pColor);
            sr.circle(gp.getgPieceCircle().x,gp.getgPieceCircle().y,gp.getgPieceCircle().radius);
        }
        sr.end();
    }

    public void drawPieces(SpriteBatch sb) {
        sb.begin();
        if (anyActiveMoves()) {
            sb.draw(activePiece.getGamePieceGfx(),activePiece.getgPieceCircle().x,activePiece.getgPieceCircle().y);
        }
        for(GamePiece gp : gamePieces) {
            sb.draw(gp.getGamePieceGfx(),gp.getgPieceCircle().x,gp.getgPieceCircle().y);
        }
        sb.end();
    }

    public int getPlayerNum() { return playerNum; }
    public GamePiece getActivePiece() { return activePiece; }
    public void nextPiece() {
        activeTurn++;
        if (anyActiveMoves()) {
            activePiece = gamePieces.first();
            gamePieces.removeIndex(0);
        }
    }

    public boolean anyActiveMoves() {
        if (activeTurn <= piecesToPlay) {
            return true;
        } else {
            return false;
        }
    }

    public int getScore() { return score; }
    public void addScore(int score) { this.score = this.score + score; }

    public int calcScore(Array<GameCell> bhs) {
        boolean excludeAdd;
        for(GamePiece gp : this.gamePieces) {
            excludeAdd = false;
            for(GameCell bh : bhs) {
                if(gp.getpLocation() == bh.getCellLoc()) {
                    excludeAdd = true;
                    break;
                }
            }
            if(!excludeAdd) { this.score = this.score + gp.getpValue(); }
        }
        return getScore();
    }

    public int getScoreSucked() { return scoreSucked; }
    public int calcBlackHole(Array<GameCell> bhs) {
        boolean excludeAdd;
        for(GamePiece gp : this.gamePieces) {
            excludeAdd = false;
            for(GameCell bh : bhs) {
                if(gp.getpLocation() == bh.getCellLoc()) {
                    excludeAdd = true;
                    break;
                }
            }
            if(excludeAdd) { this.scoreSucked = this.scoreSucked + gp.getpValue(); }
        }
        return getScoreSucked();
    }


    public Texture getBaseTexture() {
        return baseTexture;
    }

}
