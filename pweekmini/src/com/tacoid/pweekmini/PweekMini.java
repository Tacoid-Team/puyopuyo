package com.tacoid.pweekmini;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.tacoid.pweek.I18nManager;
import com.tacoid.pweek.IActivityRequestHandler;
import com.tacoid.pweek.IGameService;
import com.tacoid.pweek.MusicPlayer;
import com.tacoid.pweek.PreferenceManager;
import com.tacoid.pweek.PreferenceManager.Preference;
import com.tacoid.pweek.SoundPlayer;
import com.tacoid.pweekmini.screens.GameSoloScreen;
import com.tacoid.pweekmini.screens.GameTimeAttackScreen;
import com.tacoid.pweekmini.screens.LanguageScreen;
import com.tacoid.pweekmini.screens.LoadingScreen;
import com.tacoid.pweekmini.screens.MainMenuScreen;


public class PweekMini extends Game {
	
	private static PweekMini instance = null;
	private IActivityRequestHandler myRequestHandler;
	private IGameService myGameService;

	public AssetManager manager;
	public TextureAtlas atlasPuyo;
	public TextureAtlas atlasControls;
	public TextureAtlas atlasPlank;
	public TextureAtlas atlasPanelsLandscape;
	public TextureAtlas atlasPanelsPortrait;
	public TextureAtlas atlasBouttons;
	private boolean desktopMode;
	public static final int VIRTUAL_WIDTH = 480;
	public static final int VIRTUAL_HEIGHT = 800;
	private boolean loaded = false;
	private boolean justLaunched = true;
	private LoadingScreen loadingScreen = null;
	
	public enum ScreenOrientation {
		LANDSCAPE,
		PORTRAIT
	};
	
	private PweekMini() {}
	
	public static void setHandler(IActivityRequestHandler handler) {
		getInstance().myRequestHandler = handler;
	}
	
	public static void setGameService(IGameService gameService) {
		getInstance().myGameService = gameService;
		
	}
	
	public IGameService getGameService() {
		return getInstance().myGameService;
	}
	
	public static PweekMini getInstance() {

		if (instance == null) {
			instance = new PweekMini();
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
						setScreen(MainMenuScreen.getInstance());
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
	
	public void resize(int width, int height) {
		if (getScreen() != null){
			getScreen().resize(width, height);
		} else {
			LoadingScreen.getInstance().resize(width, height);
		}
	}
	
	@Override
	public void create() {
		loadingScreen = LoadingScreen.getInstance();
		loadingScreen.resize(0, 0);
		manager = new AssetManager();
	
		Gdx.input.setCatchBackKey(true);
		
		loadAssets();
		
		myRequestHandler.showAds(false);

		loaded = false;
        justLaunched = true;
	}
	
	private void loadAssets() {

		/* fonts */
		manager.load("images/font_score.fnt", BitmapFont.class);
		manager.load("images/font_level.fnt", BitmapFont.class);
		
		/* Textures du menu */
		manager.load("images/flag-fr.png", Texture.class);
		manager.load("images/flag-en.png", Texture.class);
		
		manager.load("images/background.png", Texture.class);
		manager.load("images/logo.png", Texture.class);
		
		atlasPuyo = new TextureAtlas(Gdx.files.internal("images/puyos/pages.atlas"));
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
		MainMenuScreen.getInstance().init();
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
