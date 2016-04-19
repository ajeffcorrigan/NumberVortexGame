package com.ajeffcorrigan.game.numbervortex.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class GamePiece {

    private static final float cellRad = 32;

    private int pValue;
    private Vector2 pLocation;
    private Circle gPieceCircle;
    private boolean inPlay;
    private Texture gamePieceGfx;

    public GamePiece(int val,Vector2 startLoc){
        this.pValue = val;
        this.inPlay = false;
        createBounds(startLoc);
    }

    public GamePiece(int val,Vector2 startLoc,Texture gpfx){
        this.pValue = val;
        this.inPlay = false;
        this.gamePieceGfx = gpfx;
        createBounds(startLoc);
    }

    public int getpValue() { return pValue; }
    public void setpValue(int pValue) { this.pValue = pValue; }

    public void createBounds(Vector2 gpStart) {
        float px;
        float py;
        if(this.gamePieceGfx != null) {
            if(pValue%2==0) {
                px = gpStart.x + ((this.gamePieceGfx.getWidth()/2)*2.2f);
                py = gpStart.y - (pValue-2) * ((this.gamePieceGfx.getWidth()/2)*1.1f);
            } else {
                px = gpStart.x;
                py = gpStart.y - (pValue-1) * ((this.gamePieceGfx.getWidth()/2)*1.1f);
            }
            gPieceCircle = new Circle(px,py,(this.gamePieceGfx.getWidth()/2));
        } else {
            if (pValue % 2 == 0) {
                px = gpStart.x + (cellRad * 2.2f);
                py = gpStart.y - (pValue - 2) * (cellRad * 1.1f);
            } else {
                px = gpStart.x;
                py = gpStart.y - (pValue - 1) * (cellRad * 1.1f);
            }
            gPieceCircle = new Circle(px, py, cellRad);
        }
    }

    public void setgPieceCircle(Vector2 newLoc) {
        getgPieceCircle().setPosition(newLoc);
    }

    public Circle getgPieceCircle() { return gPieceCircle; }
    public boolean isInPlay() { return inPlay; }
    public void setInPlay(boolean inPlay) { this.inPlay = inPlay; }
    public Vector2 getpLocation() { return pLocation; }
    public void setpLocation(Vector2 pLocation) { this.pLocation = pLocation; }
    public Texture getGamePieceGfx() { return gamePieceGfx; }
}
