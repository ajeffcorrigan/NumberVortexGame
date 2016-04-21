package com.ajeffcorrigan.game.numbervortex.tools;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GamePlayer {

    //Pieces to play for each player.
    private static final int piecesToPlay = 10;
    //Array of GamePiece objects.
    private Array<GamePiece> gamePieces;
    //Player color.
    private Color playerColor;
    //Player number.
    private int playerNum;
    //Active game piece for interacting with
    private GamePiece activePiece;

    private int activeTurn = 0;
    private int score = 0;
    private int scoreSucked = 0;
    private Array<Texture> numbers = new Array<Texture>(10);

    public GamePlayer(Vector2 startLoc,Color c,int pn) {
        this.playerColor = c;
        this.playerNum = pn;
        gamePieces = new Array<GamePiece>(piecesToPlay);
        for(int x = 1; x <= piecesToPlay; x++) { gamePieces.add(new GamePiece(x,startLoc,pn,jAssets.getTexture(c))); }
    }

    //Get and set for player score.
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    //Get for player color.
    public Color getPlayerColor() { return playerColor; }

    //Get for player number.
    public int getPlayerNum() { return playerNum; }

    //Get Array of GamePieces
    public Array<GamePiece> getGamePieces() { return gamePieces; }

    //Returns a specific GamePiece object from array
    public GamePiece getGamePiece(int gp) { return gamePieces.get(gp); }

    //Returns and removes the last GamePiece object from array
    public GamePiece popGamePiece() { return gamePieces.pop(); }

    //Draw the players game pieces to screen
    public void drawPieces(SpriteBatch sb, BitmapFont fnt) {
        sb.begin();
        if (anyActiveMoves()) {
            sb.draw(activePiece.getBasePieceTexture(),activePiece.getGamePiecePosition().x,activePiece.getGamePiecePosition().y);
            fnt.draw(sb,String.valueOf(activePiece.getPieceValue()),activePiece.getGamePiecePosition().x + (activePiece.getBasePieceTexture().getWidth()*.43f), activePiece.getGamePiecePosition().y + (activePiece.getBasePieceTexture().getHeight()*.61f));
        }
        for(GamePiece gp : gamePieces) {
            sb.draw(gp.getBasePieceTexture(),gp.getGamePiecePosition().x,gp.getGamePiecePosition().y);
            fnt.draw(sb,String.valueOf(gp.getPieceValue()),gp.getGamePiecePosition().x + (gp.getBasePieceTexture().getWidth()*.43f), gp.getGamePiecePosition().y + (gp.getBasePieceTexture().getHeight()*.61f));
        }
        sb.end();
    }

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
}
