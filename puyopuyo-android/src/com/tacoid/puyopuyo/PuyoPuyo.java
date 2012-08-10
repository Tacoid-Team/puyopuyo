package com.tacoid.puyopuyo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class PuyoPuyo extends Game {

	private static PuyoPuyo instance = null;
	public AssetManager manager;
	
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
		manager.load("images/white.png", Texture.class);
		manager.load("images/rouge.png", Texture.class);
		manager.load("images/rouge_f.png", Texture.class);
		manager.load("images/bleu.png", Texture.class);
		manager.load("images/bleu_f.png", Texture.class);
		manager.load("images/jaune.png", Texture.class);
		manager.load("images/jaune_f.png", Texture.class);
		manager.load("images/vert.png", Texture.class);
		manager.load("images/vert_f.png", Texture.class);
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
		manager.load("images/rouge_horizontal.png", Texture.class);
		manager.load("images/bleu_horizontal.png", Texture.class);
		manager.load("images/jaune_horizontal.png", Texture.class);
		manager.load("images/vert_horizontal.png", Texture.class);
		manager.load("images/rouge_vertical.png", Texture.class);
		manager.load("images/bleu_vertical.png", Texture.class);
		manager.load("images/jaune_vertical.png", Texture.class);
		manager.load("images/vert_vertical.png", Texture.class);
		manager.load("images/nuisance.png", Texture.class);
		
		manager.load("images/menu/play.png", Texture.class);
		manager.load("images/menu/exit.png", Texture.class);
		manager.load("images/menu/sky.png", Texture.class);
		manager.load("images/menu/hills.png", Texture.class);
		manager.load("images/menu/foreground.png", Texture.class);
	}
}
