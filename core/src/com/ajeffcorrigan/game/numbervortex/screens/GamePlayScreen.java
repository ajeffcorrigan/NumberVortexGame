package com.ajeffcorrigan.game.numbervortex.screens;

import com.ajeffcorrigan.game.numbervortex.NumberVortexGame;
import com.ajeffcorrigan.game.numbervortex.tools.GameBoard;
import com.ajeffcorrigan.game.numbervortex.tools.GameCell;
import com.ajeffcorrigan.game.numbervortex.tools.GameLevels;
import com.ajeffcorrigan.game.numbervortex.tools.GamePlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GamePlayScreen implements Screen {

    private NumberVortexGame game;

    private GameBoard gb;
    private GameLevels gls;
    private Array<GamePlayer> gps = new Array<GamePlayer>();
    private GamePlayer activePlayer;
    private Array<GameCell> blackHoleCells = new Array<GameCell>();
    private Vector2 blackHoleLocation;

    //basic play screen variables
    public OrthographicCamera gamecam;
    private Viewport gamePort;
    private ShapeRenderer shapeRender  = new ShapeRenderer();

    public GamePlayScreen(NumberVortexGame game) {
        this.game = game;

        //Game camera and viewport setup.
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(NumberVortexGame.gw, NumberVortexGame.gh, gamecam);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        gamecam.setToOrtho(false);

        //Gameplay objects
        gls = new GameLevels();
        //gb = new GameBoard(new Vector2(gamePort.getWorldWidth()*.08f,gamePort.getWorldHeight()*.82f),gls.getFirstLevel());
        gb = new GameBoard(new Vector2(gamePort.getWorldWidth()*.08f,gamePort.getWorldHeight()*.82f),gls.getFirstLevel(),new Texture("cell.png"));
        //gps.add(new GamePlayer(new Vector2(gamePort.getWorldWidth()*.78f,gamePort.getWorldHeight()*.42f),Color.GREEN,2));
        gps.add(new GamePlayer(new Vector2(gamePort.getWorldWidth()*.78f,gamePort.getWorldHeight()*.42f),new Texture("chipGreen.png"),2));
        gps.add(new GamePlayer(new Vector2(gamePort.getWorldWidth()*.08f,gamePort.getWorldHeight()*.42f), Color.BLUE,1));


        gps.first().nextPiece();
        gps.peek().nextPiece();

        activePlayer = gps.pop();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);

        //Clear the game screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gamecam.update();
        //gb.drawBoard(shapeRender,gamecam);
        gb.drawBoard(this.game.batch);
        if(activePlayer.getBaseTexture() == null) {
            activePlayer.drawPieces(shapeRender,gamecam);
        } else {
            activePlayer.drawPieces(this.game.batch);
        }
        for (GamePlayer gplay : gps) {
            if(gplay.getBaseTexture() == null) {
                gplay.drawPieces(shapeRender,gamecam);
            } else {
                gplay.drawPieces(this.game.batch);
            }
        }

    }

    private void update(float delta) {
        if(activePlayer.anyActiveMoves()) {
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) { Gdx.app.exit(); }
            if (Gdx.input.justTouched()) {
                Vector3 touchPoint = new Vector3();
                gamecam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                for(GameCell gc : gb.getGameCells()) {
                    if(gc.getgCellCircle().contains(touchPoint.x,touchPoint.y)) {
                        if (gc.isInPlay && !gc.isOccupied()) {
                            Gdx.app.debug(this.getClass().getSimpleName(), "Game Cell XY" + gc.getCellLoc());
                            gc.setOccupied(true);
                            activePlayer.getActivePiece().setInPlay(true);
                            activePlayer.getActivePiece().setgPieceCircle(gc.gCellCircleVec());
                            activePlayer.getActivePiece().setpLocation(gc.getCellLoc());
                            activePlayer.getGamePieces().add(activePlayer.getActivePiece());
                            activePlayer.nextPiece();
                            gps.add(activePlayer);
                            activePlayer = gps.first();
                            gps.removeIndex(0);
                        }
                    }
                }
            }
        } else {
            gps.add(activePlayer);
            for(GameCell gc : gb.getGameCells()) {
                if(gc.isInPlay && !gc.isOccupied()) {
                    Gdx.app.debug(this.getClass().getSimpleName(), "Empty cell: "+gc.getCellLoc());
                    blackHoleLocation = gc.getCellLoc();
                    break;
                }
            }
            blackHoleCells = gb.suckedIn(blackHoleLocation);
            for(GameCell egc : blackHoleCells) {
                Gdx.app.debug(this.getClass().getSimpleName(), "Sucked in cells:"+egc.getCellLoc());
            }
            for(GamePlayer gp : gps) {
                Gdx.app.debug(this.getClass().getSimpleName(), "Player: "+gp.getPlayerNum()+" Score: "+gp.calcScore(blackHoleCells));
                Gdx.app.debug(this.getClass().getSimpleName(), "Player: "+gp.getPlayerNum()+" Score Sucked: "+gp.calcBlackHole(blackHoleCells));

            }
            Gdx.app.debug(this.getClass().getSimpleName(), "Game Over!");
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
