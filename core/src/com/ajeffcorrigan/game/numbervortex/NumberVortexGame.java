package com.ajeffcorrigan.game.numbervortex;

import com.ajeffcorrigan.game.numbervortex.screens.MainMenuScreen;
import com.ajeffcorrigan.game.numbervortex.tools.GameAssetManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberVortexGame extends Game {
	public static int gw;
	public static int gh;
	public static final boolean DEBUGON = false;
	public SpriteBatch batch;
	public AssetManager manager = new AssetManager();
	public GameAssetManager gameAssets;


	@Override
	public void create() {
		Gdx.app.setLogLevel(3);
		gw = Gdx.graphics.getWidth();
		gh = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		gameAssets = new GameAssetManager();

		setScreen(new MainMenuScreen(this));
		//setScreen(new GamePlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
	}
}
