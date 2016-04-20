package com.ajeffcorrigan.game.numbervortex.screens;

import com.ajeffcorrigan.game.numbervortex.NumberVortexGame;
import com.ajeffcorrigan.game.numbervortex.tools.GameBoard;
import com.ajeffcorrigan.game.numbervortex.tools.GameCell;
import com.ajeffcorrigan.game.numbervortex.tools.GameLevels;
import com.ajeffcorrigan.game.numbervortex.tools.GamePlayLogic;
import com.ajeffcorrigan.game.numbervortex.tools.GamePlayer;
import com.ajeffcorrigan.game.numbervortex.tools.jAssets;
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

    //Main game object.
    private NumberVortexGame game;

    //GameBoard object.
    private GameBoard gameBoard;
    //Array of GamePlayer objects.
    private Array<GamePlayer> gamePlayers = new Array<GamePlayer>();
    //Game level manager
    private GameLevels gameLevels;
    //Current active player GamePlayer object.
    private GamePlayer activePlayer;
    //Game play logic
    private GamePlayLogic gamePlayLogic;

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
        gameLevels = new GameLevels();
        gamePlayLogic = new GamePlayLogic();
        gameBoard = new GameBoard(new Vector2(gamePort.getWorldWidth()*.03f,gamePort.getWorldHeight()*.87f),gameLevels.getFirstLevel(), jAssets.getTexture("cell"));
        gamePlayers.add(new GamePlayer(new Vector2(gamePort.getWorldWidth()*.78f,gamePort.getWorldHeight()*.42f),Color.GREEN,2));
        gamePlayers.add(new GamePlayer(new Vector2(gamePort.getWorldWidth()*.08f,gamePort.getWorldHeight()*.42f), Color.BLUE,1));

        gamePlayers.first().nextPiece();
        gamePlayers.peek().nextPiece();
        activePlayer = gamePlayers.pop();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);

        //Clear the game screen
        Gdx.gl.glClearColor( .80f, .80f, .85f, 0);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        gamecam.update();
        //Draw the game board.
        gameBoard.drawBoard(this.game.batch);
        activePlayer.drawPieces(this.game.batch);

        for (GamePlayer gplay : gamePlayers) {
            gplay.drawPieces(this.game.batch);
        }
    }

    private void update(float delta) {
        if(activePlayer.anyActiveMoves()) {
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) { Gdx.app.exit(); }
            if (Gdx.input.justTouched()) {
                Vector3 touchPoint = new Vector3();
                gamecam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                for(GameCell gc : gameBoard.getGameCells()) {
                    if(gc.getgCellCircle().contains(touchPoint.x,touchPoint.y) && gc.isInPlay() && !gc.isOccupied()) {
                        Gdx.app.debug(this.getClass().getSimpleName(), "Game Cell XY" + gc.getCellLoc());
                        gc.setGamePiece(activePlayer.getActivePiece());
                        activePlayer.nextPiece();
                        gamePlayers.add(activePlayer);
                        activePlayer = gamePlayers.first();
                        gamePlayers.removeIndex(0);
                    }
                }
            }
        } else {
            gamePlayers.add(activePlayer);
            for(GameCell gc : gameBoard.getGameCells()) {
                if(gc.isInPlay() && !gc.isOccupied()) {
                    Gdx.app.debug(this.getClass().getSimpleName(), "Empty cell: "+gc.getCellLoc());
                    blackHoleLocation = gc.getCellLoc();
                    break;
                }
            }
            gameBoard.eliminateCells(blackHoleLocation);
            for(GamePlayer gp : gamePlayers) {
                gp.setScore(gameBoard.getScore(gp.getPlayerNum()));
                Gdx.app.debug(this.getClass().getSimpleName(), "Player: "+gp.getPlayerNum()+" Score: "+ gameBoard.getScore(gp.getPlayerNum()));
            }
            if(gamePlayLogic.getWinner(gamePlayers) == 99) {
                Gdx.app.debug(this.getClass().getSimpleName(), "Game ends in a tie!");
                Gdx.app.exit();
            } else {
                Gdx.app.debug(this.getClass().getSimpleName(), "Winner is player "+gamePlayLogic.getWinner(gamePlayers)+"!");
                Gdx.app.exit();
            }
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
