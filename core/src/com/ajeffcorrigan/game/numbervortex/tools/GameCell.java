package com.ajeffcorrigan.game.numbervortex.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class GameCell {

    private static final float cellRad = 30;

    private boolean isOccupied;
    private Vector2 cellLoc;
    private Circle gCellCircle;
    private Vector2 gCellVector;
    private GamePiece gamePiece;

    public boolean isInPlay;

    public GameCell(Vector2 cl, Vector2 startLoc) {
        isOccupied = false;
        isInPlay = false;
        cellLoc = cl;
        createBounds(startLoc);
    }

    public void drawCircle(ShapeRenderer sr) {
        if(this.isInPlay) {
            sr.setColor(Color.WHITE);
        } else {
            sr.setColor(Color.RED);
        }
        sr.circle(gCellCircle.x,gCellCircle.y,gCellCircle.radius);
    }

    public void drawInPlayBoard(ShapeRenderer sr) {
        if(this.isInPlay) {
            sr.setColor(Color.WHITE);
            sr.circle(gCellCircle.x,gCellCircle.y,gCellCircle.radius);
        }
    }

    public void createBounds(Vector2 gbStart) {
        float tx;
        float ty;
        if(cellLoc.x % 2 == 0) {
            tx = gbStart.x + (cellRad*2) * (cellLoc.y);
        } else {
            tx = gbStart.x + ((cellRad*2) * (cellLoc.y))+(cellRad);
        }
        ty = gbStart.y - (cellRad*1.92f) * (cellLoc.x);
        gCellCircle = new Circle(tx,ty,cellRad);
    }

    public void createBounds(Vector2 gbStart, Texture gcText) {
        float tx;
        float ty;
        if(cellLoc.x % 2 == 0) {
            tx = gbStart.x + (cellRad*2) * (cellLoc.y);
        } else {
            tx = gbStart.x + ((cellRad*2) * (cellLoc.y))+(cellRad);
        }
        ty = gbStart.y - (cellRad*1.8f) * (cellLoc.x);
        gCellCircle = new Circle(tx,ty,cellRad);
    }

    //Get and set for is cell playable.
    public boolean isInPlay() { return isInPlay; }
    public void setInPlay(boolean inPlay) { isInPlay = inPlay; }

    //Get and set for cell location.
    public Vector2 getCellLoc() { return cellLoc; }
    public void setCellLoc(Vector2 cellLoc) { this.cellLoc = cellLoc; }

    public Circle getgCellCircle() { return gCellCircle; }
    public Vector2 gCellCircleVec() { return new Vector2(gCellCircle.x,gCellCircle.y); }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}