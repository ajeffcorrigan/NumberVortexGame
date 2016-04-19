package com.ajeffcorrigan.game.numbervortex.screens;

import com.ajeffcorrigan.game.numbervortex.NumberVortexGame;
import com.ajeffcorrigan.game.numbervortex.tools.ScreenObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {

    private NumberVortexGame game;

    //basic play screen variables
    public OrthographicCamera gamecam;
    private Viewport gamePort;

    public Array<ScreenObject> screenObjectArray;

    public MainMenuScreen(NumberVortexGame game) {
        this.game = game;

        //Game camera and viewport setup.
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(NumberVortexGame.gw, NumberVortexGame.gh, gamecam);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        screenObjectArray = new Array<ScreenObject>();
        screenObjectArray.add(new ScreenObject(new Texture("spacebackground.jpg"),new Vector2(0,0)));
        screenObjectArray.add(new ScreenObject(new Texture("pvpbutton.png"),new Vector2(gamePort.getWorldWidth()*.25f,gamePort.getWorldHeight()*.60f),0));
        screenObjectArray.add(new ScreenObject(new Texture("pvcbutton.png"),new Vector2(gamePort.getWorldWidth()*.25f,gamePort.getWorldHeight()*.50f),1));

        gamecam.setToOrtho(false);

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

        game.batch.begin();
        for (ScreenObject so:screenObjectArray) { so.draw(game.batch); }
        game.batch.end();
    }

    private void update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) { Gdx.app.exit(); }
        if (Gdx.input.justTouched()) {
            Vector3 touchPoint = new Vector3();
            gamecam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));
            //Gdx.app.log(this.getClass().getSimpleName(),"Window touched.");
            //Gdx.app.log(this.getClass().getSimpleName(),"Touch x: " + Gdx.input.getX() +" touch y: " + Gdx.input.getY());
            //Gdx.app.log(this.getClass().getSimpleName(),"TouchPoint x: " + touchPoint.x +" touch y: " + touchPoint.y);
            for (ScreenObject so:screenObjectArray) {
                if(so.getBoundingRectangle().contains(touchPoint.x, touchPoint.y) && so.isActionItem) {
                    //Gdx.app.log(this.getClass().getSimpleName(),so.getClass().getSimpleName());
                    Gdx.app.log(this.getClass().getSimpleName(),"Action code: " + so.actionCode);
                    if(so.actionCode == 0) {
                        game.setScreen(new GamePlayScreen(game));
                        this.hide();
                    }
                }
            }
        }
        screenObjectArray.first().randomMoveXY(delta);
        gamecam.update();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        gamecam.position.set(gamecam.viewportWidth / 2, gamecam.viewportHeight / 2, 0);
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
