package com.tacoid.pweek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.tacoid.pweek.PreferenceManager.Preference;
import com.tacoid.pweek.screens.GameServicesScreen;
import com.tacoid.pweek.screens.GameSoloScreen;
import com.tacoid.pweek.screens.GameTimeAttackScreen;
import com.tacoid.pweek.screens.GameVersusScreen;
import com.tacoid.pweek.screens.LanguageScreen;
import com.tacoid.pweek.screens.LoadingScreen;
import com.tacoid.pweek.screens.MainMenuScreen;

public class Pweek extends Game {
	
	private IActivityRequestHandler myRequestHandler;
	private IGameService myGameService;

	private static Pweek instance = null;
	public AssetManager manager;
	public TextureAtlas atlasPuyo;
	public TextureAtlas atlasControls;
	public TextureAtlas atlasPlank;
	public TextureAtlas atlasPanelsLandscape;
	public TextureAtlas atlasPanelsPortrait;
	public TextureAtlas atlasBouttons;
	public TextureAtlas atlasGoogle;
	private boolean desktopMode;
	public static final int VIRTUAL_WIDTH = 1280;
	public static final int VIRTUAL_HEIGHT = 768;
	public ShareLauncher shareLauncher = null;
	private LoadingScreen loadingScreen;
	private boolean loaded = false;
	private boolean justLaunched = true;
	
	public enum ScreenOrientation {
		LANDSCAPE,
		PORTRAIT
	};
	
	private Pweek() {}
	
	public static void setHandler(IActivityRequestHandler handler) {
		getInstance().myRequestHandler = handler;
	}
	
	public static void setGameService(IGameService gameService) {
		getInstance().myGameService = gameService;
		
	}
	
	public IGameService getGameService() {
		return getInstance().myGameService;
	}
	
	/*
	public static void setShareLauncher(ShareLauncher launcher) {
		getInstance().shareLauncher = launcher;
	}*/
	
	public static Pweek getInstance() {

		if (instance == null) {
			instance = new Pweek();
		}
		return instance;
	}
	
	public IActivityRequestHandler getHandler() {
		return myRequestHandler;
	}
	
	public void render() {
		/* Si update renvoi true, c'est que tout est chargé, on a plus qu'à afficher le screen qu'on veut. Sinon, on affiche le screen de chargement */
		if (manager.update()) {
			if (justLaunched) {
				String language = PreferenceManager.getInstance().getPreference(Preference.LANGUAGE);
				MusicPlayer.getInstance().init(manager.get("sounds/AnoyingMusic.mp3", Music.class));
				SoundPlayer.getInstance().init(manager);
				
				if(I18nManager.getInstance().setLanguage(language)) {
					loadLocalizedAssets();
					if (getScreen() == null) {
						setScreen(GameServicesScreen.getInstance());
						myRequestHandler.showAds(true);
					} else {
						getScreen().show();
					}
				} else {
					setScreen(LanguageScreen.getInstance());
				}
				justLaunched = false;
			} else if (!loaded){
				getScreen().show();
			}
			loaded = true;
			super.render();
		} else {
			if (loadingScreen != null) loadingScreen.render(Gdx.graphics.getDeltaTime());
			loaded = false;
		}
	}
	
	public void resize (int width, int height) {
		if (getScreen() != null){
			getScreen().resize(width, height);
		}
		else {
			LoadingScreen.getInstance().resize(width, height);
		}
	}
	
	@Override
	public void create() {		
		
		loadingScreen = LoadingScreen.getInstance();		
		LoadingScreen.getInstance().resize(0, 0);

		manager = new AssetManager();
	
		Gdx.input.setCatchBackKey(true);
		
		loadAssets();
		
		myRequestHandler.showAds(false);

        justLaunched = true;
	}
	
	private void loadAssets() {

		/* fonts */
		manager.load("images/font_score.fnt", BitmapFont.class);
		manager.load("images/font_level.fnt", BitmapFont.class);
		manager.load("images/font64.fnt", BitmapFont.class);
		
		/* Textures du menu */
		manager.load("images/menu/flag-fr.png", Texture.class);
		manager.load("images/menu/flag-en.png", Texture.class);
		manager.load("images/menu/sky.png", Texture.class);
		manager.load("images/menu/sky-portrait.png", Texture.class);
		manager.load("images/menu/hills.png", Texture.class);
		manager.load("images/menu/logo.png", Texture.class);
		
		atlasPuyo = new TextureAtlas(Gdx.files.internal("images/puyos/pages.atlas"));
		atlasControls = new TextureAtlas(Gdx.files.internal("images/controls/pages.atlas"));
		atlasPanelsLandscape = new TextureAtlas(Gdx.files.internal("images/panels/landscape/pages.atlas"));
		atlasPanelsPortrait = new TextureAtlas(Gdx.files.internal("images/panels/portrait/pages.atlas"));
		atlasBouttons = new TextureAtlas(Gdx.files.internal("images/bouttons/pages.atlas"));
		
		/*** Son ***/
		manager.load("sounds/bleep.wav", Sound.class);
		manager.load("sounds/bleep2.wav", Sound.class);
		manager.load("sounds/explode.wav", Sound.class);
		manager.load("sounds/click.wav", Sound.class);
		manager.load("sounds/nuisance.wav", Sound.class);
		
		manager.load("sounds/AnoyingMusic.mp3", Music.class);
		
	}

	public void loadLocalizedAssets() {

		atlasPlank = new TextureAtlas(Gdx.files.internal("images/menu/plank-" + I18nManager.getInstance().getLanguage().toString() + "/pages.atlas"));
		atlasGoogle = new TextureAtlas(Gdx.files.internal("images/google/" + I18nManager.getInstance().getLanguage().toString() + "/pages.atlas"));

		MainMenuScreen.getInstance().init();
		GameVersusScreen.getInstance().initGraphics();
		GameSoloScreen.getInstance().initGraphics();
		GameTimeAttackScreen.getInstance().initGraphics();
	}

	public void setDesktopMode(boolean b) {
		this.desktopMode = b;
	}
	
	public boolean getDesktopMode() {
		return this.desktopMode;
	}
}
