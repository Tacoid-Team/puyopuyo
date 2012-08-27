package com.tacoid.puyopuyo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class PuyoPuyo extends Game {

	private static PuyoPuyo instance = null;
	public AssetManager manager;
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
		
		manager.load("images/puyos/white.png", Texture.class);
		manager.load("images/puyos/rouge.png", Texture.class);
		manager.load("images/puyos/rouge_f.png", Texture.class);
		manager.load("images/puyos/red_fall_48.png", Texture.class);
		manager.load("images/puyos/bleu.png", Texture.class);
		manager.load("images/puyos/bleu_f.png", Texture.class);
		manager.load("images/puyos/jaune.png", Texture.class);
		manager.load("images/puyos/jaune_f.png", Texture.class);
		manager.load("images/puyos/vert.png", Texture.class);
		manager.load("images/puyos/vert_f.png", Texture.class);
		
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
		
		manager.load("images/puyos/rouge_horizontal.png", Texture.class);
		manager.load("images/puyos/bleu_horizontal.png", Texture.class);
		manager.load("images/puyos/jaune_horizontal.png", Texture.class);
		manager.load("images/puyos/vert_horizontal.png", Texture.class);
		manager.load("images/puyos/rouge_vertical.png", Texture.class);
		manager.load("images/puyos/bleu_vertical.png", Texture.class);
		manager.load("images/puyos/jaune_vertical.png", Texture.class);
		manager.load("images/puyos/vert_vertical.png", Texture.class);
		manager.load("images/puyos/nuisance.png", Texture.class);
		
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
	}
}
