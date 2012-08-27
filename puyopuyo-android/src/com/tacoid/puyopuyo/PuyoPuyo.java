package com.tacoid.puyopuyo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class PuyoPuyo extends Game {

	private static PuyoPuyo instance = null;
	public AssetManager manager;
	public TextureAtlas atlasPuyo;
	public static final int VIRTUAL_WIDTH = 1280;
	public static final int VIRTUAL_HEIGHT = 768;
	
	private PuyoPuyo() {}
	
	public static PuyoPuyo getInstance() {
		if (instance == null) {
			instance = new PuyoPuyo();
		}
		return instance;
	}
	
	@Override
	public void create() {
		setScreen(LoadingScreen.getInstance());
	
		Gdx.input.setCatchBackKey(true);
		
		loadAssets();
	}

	private void loadAssets() {
		manager = new AssetManager();

		manager.load("images/fond.png", Texture.class);
		manager.load("images/fond_solo.png", Texture.class);
		
		manager.load("images/down.png", Texture.class);
		manager.load("images/down_down.png", Texture.class);
		manager.load("images/left.png", Texture.class);
		manager.load("images/left_down.png", Texture.class);
		manager.load("images/right.png", Texture.class);
		manager.load("images/right_down.png", Texture.class);
		manager.load("images/rotleft.png", Texture.class);
		manager.load("images/rotright.png", Texture.class);
		manager.load("images/rotleft_down.png", Texture.class);
		manager.load("images/rotright_down.png", Texture.class);
		
		manager.load("images/perdu-fr.png", Texture.class);
		manager.load("images/gagne-fr.png", Texture.class);
		manager.load("images/continuer-fr.png", Texture.class);
		manager.load("images/quitter-fr.png", Texture.class);
		
		manager.load("images/pause_button.png", Texture.class);
		
		/* Textures du menu */
		manager.load("images/menu/solo.png", Texture.class);
		manager.load("images/menu/versusia.png", Texture.class);
		manager.load("images/menu/chrono.png", Texture.class);
		manager.load("images/menu/exit.png", Texture.class);
		manager.load("images/menu/sky.png", Texture.class);
		manager.load("images/menu/hills.png", Texture.class);
		manager.load("images/menu/foreground.png", Texture.class);
		
		atlasPuyo = new TextureAtlas(Gdx.files.internal("images/puyos/pages.atlas"));
	}
}
