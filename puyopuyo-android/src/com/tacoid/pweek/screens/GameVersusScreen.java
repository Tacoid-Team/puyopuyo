package com.tacoid.pweek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.Controller;
import com.tacoid.pweek.MusicPlayer;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.SoundPlayer;
import com.tacoid.pweek.IGameService.Achievement;
import com.tacoid.pweek.Pweek.ScreenOrientation;
import com.tacoid.pweek.ScoreManager.GameType;
import com.tacoid.pweek.SoundPlayer.SoundType;
import com.tacoid.pweek.actors.BackgroundActor;
import com.tacoid.pweek.actors.ControlerActor;
import com.tacoid.pweek.actors.ExplosionActor;
import com.tacoid.pweek.actors.GameOverActor;
import com.tacoid.pweek.actors.GridActor;
import com.tacoid.pweek.actors.LandscapePanelActor;
import com.tacoid.pweek.actors.MusicButtonActor;
import com.tacoid.pweek.actors.NextPieceActor;
import com.tacoid.pweek.actors.PauseMenu;
import com.tacoid.pweek.actors.ScoreActor;
import com.tacoid.pweek.actors.SoundButtonActor;
import com.tacoid.pweek.actors.StartActor;
import com.tacoid.pweek.actors.GameOverActor.GameOverType;
import com.tacoid.pweek.logic.GameLogic;
import com.tacoid.pweek.logic.IA;
import com.tacoid.pweek.logic.IAEasy;
import com.tacoid.pweek.logic.IAHard;
import com.tacoid.pweek.logic.State;
import com.tacoid.tracking.TrackingManager;

public class GameVersusScreen implements GameScreen {
	private static final int VIRTUAL_WIDTH = 1280;
	private static final int VIRTUAL_HEIGHT = 768;
	private static GameVersusScreen instance = null;
	private Stage stage;
	private GameLogic gameLogic;
	private GameLogic gameLogicIA;
	private GridActor gridActor;
	private GridActor gridActorIA;
	private NextPieceActor nextPieceActor;
	private NextPieceActor nextPieceActorIA;
	protected float elapsedTime;

	private int puyoSize = 48;

	private IA ia;
	private InputProcessor controller;
	private ControlerActor controllerActor;
	private PauseMenu pauseMenu;
	private PauseButton pauseButton;
	private StartActor startActor;

	private GameOverActor gameOver;
	private boolean gamePaused;

	private int level;
	private ExplosionActor explosionActor;
	private ExplosionActor explosionActorIA;
	private boolean started;
	private int volumeStart;

	private void addButton(Button button, int x, int y) {
		stage.addActor(button);
		button.setX(x);
		button.setY(y);
	}

	public class PauseButton extends Button {

		public PauseButton(TextureRegion region) {
			super(new TextureRegionDrawable(region));

			addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
					pauseMenu.show();
					return true;
				}
			});
		}
	}

	private GameVersusScreen() {
		gameLogic = new GameLogic(Pweek.getInstance().getGameService(), false);
		gameLogicIA = new GameLogic(Pweek.getInstance().getGameService(), true);
		gameLogic.setOpponent(gameLogicIA);
		gameLogicIA.setOpponent(gameLogic);
		elapsedTime = 0;
	}

	public int getPuyoSize() {
		return puyoSize;
	}

	public void setLevel(int level) {
		this.level = level;
		if (level == 0) {
			gameLogic.setInitialSpeed(0.5f);
			gameLogicIA.setInitialSpeed(0.5f);
			gameLogic.setNColors(3);
			gameLogicIA.setNColors(4);
			ia = new IAEasy(gameLogicIA);
		} else if (level == 1) {
			gameLogic.setInitialSpeed(0.5f);
			gameLogicIA.setInitialSpeed(0.5f);
			gameLogic.setNColors(4);
			gameLogicIA.setNColors(4);
			ia = new IAHard(gameLogicIA);
		} else if (level == 2) {
			gameLogic.setInitialSpeed(0.3f);
			gameLogicIA.setInitialSpeed(0.3f);
			gameLogic.setNColors(4);
			gameLogicIA.setNColors(4);
			gameLogicIA.setCheatMode(true);
			ia = new IAHard(gameLogicIA);
		} else if (level == 3) {
			gameLogic.setInitialSpeed(0.15f);
			gameLogicIA.setInitialSpeed(0.3f);
			gameLogic.setNColors(5);
			gameLogicIA.setNColors(5);
			gameLogicIA.setCheatMode(true);
			ia = new IAHard(gameLogicIA);
		}
	}
	public int getLevel() {
		return level;
	}

	public void initGraphics() {

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		controller = new Controller(gameLogic, this, stage, new Vector2(290, 660), new Vector2(990, 5));

		gridActor = new GridActor(Pweek.getInstance().atlasPuyo, gameLogic,Pweek.getInstance().manager.get("images/font_score.fnt", BitmapFont.class),  296, 26, 54, puyoSize);
		explosionActor = new ExplosionActor(Pweek.getInstance().atlasPuyo, gameLogic,Pweek.getInstance().manager.get("images/font_score.fnt", BitmapFont.class),  296, 26, 54, puyoSize);
		nextPieceActor = new NextPieceActor(Pweek.getInstance().atlasPuyo, gameLogic, 95, 500, puyoSize);
		ScoreActor scoreActor = new ScoreActor(Pweek.getInstance().manager.get("images/font_score.fnt", BitmapFont.class), gameLogic, 550, 743);

		gridActorIA = new GridActor(Pweek.getInstance().atlasPuyo, gameLogicIA,Pweek.getInstance().manager.get("images/font_score.fnt", BitmapFont.class),  650, 26, 54, puyoSize);
		explosionActorIA = new ExplosionActor(Pweek.getInstance().atlasPuyo, gameLogicIA,Pweek.getInstance().manager.get("images/font_score.fnt", BitmapFont.class),  650, 26, 54, puyoSize);
		nextPieceActorIA = new NextPieceActor(Pweek.getInstance().atlasPuyo, gameLogicIA, 1066, 500, puyoSize);
		ScoreActor scoreActorIA = new ScoreActor(Pweek.getInstance().manager.get("images/font_score.fnt", BitmapFont.class), gameLogicIA, 830, 743);

		TextureRegion pauseRegion = new TextureRegion(Pweek.getInstance().atlasBouttons.findRegion("pause_button"));

		stage.addActor(new BackgroundActor(ScreenOrientation.LANDSCAPE));
		stage.addActor(new LandscapePanelActor());

		stage.addActor(gridActor);
		stage.addActor(nextPieceActor);
		stage.addActor(scoreActor);
		stage.addActor(gridActorIA);
		stage.addActor(nextPieceActorIA);
		stage.addActor(scoreActorIA);
		
		controllerActor = new ControlerActor(ScreenOrientation.LANDSCAPE, gameLogic);
		stage.addActor(controllerActor);

		stage.addActor(explosionActorIA);
		stage.addActor(explosionActor);
		
		pauseButton = new PauseButton(pauseRegion);
		addButton(pauseButton,10,VIRTUAL_HEIGHT-10-pauseRegion.getRegionHeight());
		addButton(MusicButtonActor.createMusicButton(Pweek.getInstance().atlasBouttons),VIRTUAL_WIDTH-90, VIRTUAL_HEIGHT-90);
		addButton(SoundButtonActor.createSoundButton(Pweek.getInstance().atlasBouttons),VIRTUAL_WIDTH-180, VIRTUAL_HEIGHT-90);


		boolean show = false;
		if (gameOver != null) {
			show = gameOver.isVisible();
		}
		gameOver = new GameOverActor(Pweek.getInstance().atlasPlank, Pweek.getInstance().atlasPanelsLandscape, Pweek.getInstance().atlasBouttons, Pweek.getInstance().manager.get("images/font_score.fnt", BitmapFont.class), this, VIRTUAL_WIDTH/2, 8*VIRTUAL_HEIGHT/9-25);

		stage.addActor(gameOver);
		if (show) {
			gameOver();
		} else {
			gameOver.hide();
		}

		pauseMenu = new PauseMenu(Pweek.getInstance().atlasPlank, this, Pweek.getInstance().getHandler(), ScreenOrientation.LANDSCAPE, !gamePaused);
		stage.addActor(pauseMenu);

		show = true;
		if (startActor != null) {
			show = startActor.isVisible();
		}

		startActor = new StartActor(Pweek.getInstance().atlasPlank, this);
		if (show) {
			startActor.show();
		} else {
			startActor.setVisible(false);
			startActor.setTouchable(Touchable.disabled);
			if (gamePaused) {
				pauseMenu.show();
			}
		}
		stage.addActor(startActor);
	}

	public static GameVersusScreen getInstance() {
		if (instance == null) {
			instance = new GameVersusScreen();
		}
		return instance;
	}

	public static void freeInstance() {
		instance = null;
	}

	@Override
	public void dispose() {
		Gdx.app.log("Versus", "paused");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		if (!gamePaused && gameLogic.getState() != State.LOST) {
			pauseMenu.show();
		}
	}

	@Override
	public void render(float delta) {
		GLCommon gl = Gdx.gl;

		if (gameLogic.getState() != State.LOST
				&& gameLogicIA.getState() != State.LOST) {
			if (!gamePaused) {
				this.elapsedTime += delta;
			}

			// Update model
			gameLogic.update(delta);
			gameLogicIA.update(delta);
			ia.update(delta);
			if (gameLogic.getState() == State.LOST
					|| gameLogicIA.getState() == State.LOST) {
				gameOver();
			}
		}

		// clear previous frame
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(int arg0, int arg1) {
		stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		stage.getCamera().position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
	}

	@Override
	public void resume() {
		show();
	}

	@Override
	public void show() {
		Pweek.getInstance().getHandler().setPortrait(false);
		Pweek.getInstance().getHandler().showAds(!startActor.isVisible() && (gamePaused || gameOver.isVisible()));
		resize(0, 0);
		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void init() {
		elapsedTime = 0;
		started = false;
		gameLogic.init();
		gameLogicIA.init();
		gameOver.hide();
		startActor.show();
	}

	private void gameOver() {
		int volumeEnd;
		if (!MusicPlayer.getInstance().isMuted()) {
			volumeEnd = Pweek.getInstance().getHandler().getVolume();
		} else {
			volumeEnd = 0;
		}
		if (volumeEnd > 80 && volumeStart > 80 && elapsedTime > 600) {
			Pweek.getInstance().getGameService().unlockAchievement(Achievement.DEAF);
		}
		
		if (gameLogic.getState() == State.LOST){
			gameOver.show(GameOverType.LOSE);
		} else {
			if (level == 3) {
				Pweek.getInstance().getGameService().unlockAchievement(Achievement.FOREVER_ALONE);
			}
			gameOver.show(GameOverType.WIN);
		}
		controllerActor.setTouchable(Touchable.disabled);
		pauseButton.setTouchable(Touchable.disabled);
	}

	@Override
	public void gamePause() {
		gamePaused = true;
		gameLogic.pause();
		gameLogicIA.pause();

		gridActor.setVisible(false);
		gridActorIA.setVisible(false);
		nextPieceActor.setVisible(false);
		nextPieceActorIA.setVisible(false);
		controllerActor.setTouchable(Touchable.disabled);
		pauseButton.setTouchable(Touchable.disabled);
	}

	public void gameStart() {
		if (!MusicPlayer.getInstance().isMuted()) {
			this.volumeStart = Pweek.getInstance().getHandler().getVolume();
		} else {
			this.volumeStart = 0;
		}
		this.gameResume();
		this.started = true;
	}
	
	@Override
	public void gameResume() {
		gamePaused = false;
		gameLogic.resume();
		gameLogicIA.resume();


		gridActor.setVisible(true);
		gridActorIA.setVisible(true);
		nextPieceActor.setVisible(true);
		nextPieceActorIA.setVisible(true);
		controllerActor.setTouchable(Touchable.enabled);
		pauseButton.setTouchable(Touchable.enabled);
	}

	@Override
	public ScreenOrientation getOrientation() {
		return ScreenOrientation.LANDSCAPE;
	}

	@Override
	public float getHeight() {
		return VIRTUAL_HEIGHT;
	}

	@Override
	public float getWidth() {
		return VIRTUAL_WIDTH;
	}

	@Override
	public GameType getGameType() {
		return GameType.VERSUS_IA;
	}

	@Override
	public int getScore() {
		if (gameLogic.getState() == State.LOST) {
			return 0;
		} else {
			return gameLogic.getScore() + getTimeBonus();
		}
	}

	public int getTimeBonus() {
		return Math.max(0, 25000 - (int)(elapsedTime * 50));
	}


	@Override
	public float getElapsedTime() {
		return elapsedTime;
	}

	@Override
	public void quit() {
		pauseMenu.hide();
		GameType type = getGameType();
		TrackingManager.getTracker().trackEvent("UI", "button_click",type.toString()+" Level "+ getLevel() +" quit before end", null);
		Pweek.getInstance().setScreen(MainMenuScreen.getInstance());
	}

	@Override
	public boolean isGamePaused() {
		return gamePaused;
	}

	@Override
	public void hidePause() {
		pauseMenu.hide();
	}

	@Override
	public boolean isGameStarted() {
		return started;
	}
}
