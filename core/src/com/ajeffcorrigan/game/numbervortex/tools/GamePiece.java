package com.ajeffcorrigan.game.numbervortex.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class GamePiece {

    private static final float cellRad = 30;

    //Value of the game piece
    private int pieceValue;
    //Circle object for game piece.
    private Circle gPieceCircle;
    //Has the game piece been played.
    private boolean inPlay;
    //Which player owns this piece.
    private int ownerOfPiece;
    //Base texture for game piece.
    private Texture basePieceTexture;
    //Location of piece based on game board grid.
    private Vector2 gamePieceGridLocation;
    //Location of game piece on screen
    private Vector2 gamePiecePosition;

    public GamePiece(int val,Vector2 sl,int po){
        this.pieceValue = val;
        this.inPlay = false;
        this.ownerOfPiece = po;
        this.basePieceTexture = null;
        createBounds(sl);
    }

    public GamePiece(int val,Vector2 startLoc, int po, Texture bt){
        this.pieceValue = val;
        this.inPlay = false;
        this.ownerOfPiece = po;
        this.basePieceTexture = bt;
        createBounds(startLoc);
        this.gamePiecePosition = new Vector2(gPieceCircle.x - gPieceCircle.radius,gPieceCircle.y - gPieceCircle.radius);
    }

    //Get and set the owner of the game piece.
    public int getOwnerOfPiece() { return ownerOfPiece; }
    public void setOwnerOfPiece(int ownerOfPiece) { this.ownerOfPiece = ownerOfPiece; }

    //Get and set of game piece value.
    public int getPieceValue() { return pieceValue; }
    public void setPieceValue(int pValue) { this.pieceValue = pValue; }

    //Get and set for is piece in play.
    public boolean isInPlay() { return inPlay; }
    public void setInPlay(boolean inPlay) { this.inPlay = inPlay; }

    //Get and set for base game piece texture.
    public Texture getBasePieceTexture() { return basePieceTexture; }
    public void setBasePieceTexture(Texture basePieceTexture) { this.basePieceTexture = basePieceTexture; }

    //Get and set for Circle object.
    public Circle getgPieceCircle() { return gPieceCircle; }
    public void setgPieceCircle(Circle gPieceCircle) { this.gPieceCircle = gPieceCircle; }

    //Get and set for game piece grid location.
    public Vector2 getGamePieceGridLocation() { return gamePieceGridLocation; }
    public void setGamePieceGridLocation(Vector2 gamePieceGridLocation) { this.gamePieceGridLocation = gamePieceGridLocation; }

    //Helper function for creating the bounds of the piece object.
    public void createBounds(Vector2 gpStart) {
        float px;
        float py;
        float rad = this.basePieceTexture.getWidth() / 2f;
        if (this.pieceValue % 2 == 0) {
            px = gpStart.x + (rad * 2.2f);
            py = gpStart.y - (this.pieceValue - 2) * (rad * 1.1f);
        } else {
            px = gpStart.x;
            py = gpStart.y - (this.pieceValue - 1) * (rad * 1.1f);
        }
        gPieceCircle = new Circle(px, py, rad);
    }

    //Sets new location of game piece and circle object.
    public void setPieceLocation(Vector2 nl) { this.gPieceCircle.setPosition(nl); }

    //Get and set for game piece position on screen
    public Vector2 getGamePiecePosition() { return gamePiecePosition; }
    public void setGamePiecePosition(Vector2 gamePiecePosition) { this.gamePiecePosition = gamePiecePosition; }
}
