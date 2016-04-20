package com.ajeffcorrigan.game.numbervortex.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class GameCell {

    //Is the cell currently occupied.
    private boolean isOccupied;
    //Location of the cell at the grid level.
    private Vector2 cellLoc;
    //Cell position on screen, based on x/y 0.
    private Vector2 cellPosition;
    //Circle object of cell
    private Circle gCellCircle;
    //Game piece occupying the cell.
    private GamePiece gamePiece;
    //Is the cell playable at all, if not don't draw it.
    private boolean isInPlay;
    //Is this cell to be sucked into the vortex.
    private boolean isSuckedIn;
    //Texture for cell
    private Texture cellTexture;

    public GameCell(Vector2 cl, Vector2 startLoc, boolean ip, Texture ct) {
        this.isOccupied = false;
        this.isInPlay = ip;
        this.cellLoc = cl;
        this.cellTexture = ct;
        createBounds(startLoc);
        this.cellPosition = new Vector2(gCellCircle.x - gCellCircle.radius,gCellCircle.y - gCellCircle.radius);
        Gdx.app.debug(this.getClass().getSimpleName(),"Cell position for "+ this.cellLoc +": "+ this.cellPosition);
    }

    //Get and set for is cell playable.
    public boolean isInPlay() { return isInPlay; }
    public void setInPlay(boolean inPlay) { isInPlay = inPlay; }

    //Get and set for is game cell occupied.
    public boolean isOccupied() {
        return isOccupied;
    }
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    //Get and set for cell location.
    public Vector2 getCellLoc() { return cellLoc; }
    public void setCellLoc(Vector2 cellLoc) { this.cellLoc = cellLoc; }

    //Get and set for if the cell is sucked in.
    public boolean isSuckedIn() { return isSuckedIn; }
    public void setSuckedIn(boolean suckedIn) { isSuckedIn = suckedIn; }

    //Get and set for game piece
    public GamePiece getGamePiece() { return gamePiece; }
    public void setGamePiece(GamePiece gamePiece) {
        this.gamePiece = gamePiece;
        this.isOccupied = true;
        this.gamePiece.setInPlay(true);
        this.gamePiece.setGamePieceGridLocation(cellLoc);
        this.gamePiece.setPieceLocation(new Vector2(gCellCircle.x, gCellCircle.y));
    }

    public void createBounds(Vector2 gbStart) {
        float tx;
        float ty;
        float rad = this.cellTexture.getWidth() / 2f;

        if(cellLoc.x % 2 == 0) {
            tx = gbStart.x + (rad*2) * (cellLoc.y);
        } else {
            tx = gbStart.x + ((rad*2) * (cellLoc.y))+(rad);
        }
        ty = gbStart.y - (rad*1.8f) * (cellLoc.x);
        Gdx.app.debug(this.getClass().getSimpleName(),"Circle bound location for "+ this.cellLoc +": "+ tx +", "+ ty);
        gCellCircle = new Circle(tx,ty,rad);
    }

    public Circle getgCellCircle() { return gCellCircle; }
    public Vector2 gCellCircleVec() { return new Vector2(gCellCircle.x,gCellCircle.y); }

    //Get and set for cell position on screen
    public Vector2 getCellPosition() { return cellPosition; }
    public void setCellPosition(Vector2 cellPosition) { this.cellPosition = cellPosition; }

    //Get empty cell texture
    public Texture getCellTexture() { return cellTexture; }
}