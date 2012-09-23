package com.tacoid.puyopuyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;
import com.tacoid.puyopuyo.SoundPlayer.SoundType;
import com.tacoid.puyopuyo.actors.BackgroundActor;
import com.tacoid.puyopuyo.actors.ControlerActor;
import com.tacoid.puyopuyo.actors.GridActor;
import com.tacoid.puyopuyo.actors.MusicButtonActor;
import com.tacoid.puyopuyo.actors.NextPieceActor;
import com.tacoid.puyopuyo.actors.PauseMenu;
import com.tacoid.puyopuyo.actors.PortraitPanelActor;
import com.tacoid.puyopuyo.actors.ScoreActor;
import com.tacoid.puyopuyo.logic.GameLogic;
import com.tacoid.puyopuyo.logic.State;

public abstract class GameScreenPortrait implements GameScreen {
	
	private static final int VIRTUAL_WIDTH = 768;
	private static final int VIRTUAL_HEIGHT = 1280;

	protected boolean end = false;
	protected float elapsedTime;
	protected PauseMenu pauseMenu;
	protected PauseButton pauseButton;
	protected Stage stage;
	protected GameLogic gameLogic;
	
	private GridActor gridActor;
	private InputProcessor controller;
	private ControlerActor controllerActor;
	protected boolean gamePaused;
	
	protected class PauseButton extends Button {

		public PauseButton(TextureRegion region) {
			super(region);
		}
		
		public boolean touchDown(float x, float y, int pointer) {
			SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
			pauseMenu.show();
			return true;
		}

	}

	private void addButton(Button button, int x, int y) {
		stage.addActor(button);
		button.x = x;
		button.y = y;
	}

	protected void initGraphics() {
		elapsedTime = 0;
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		gameLogic = new GameLogic();
		controller = new Controller(gameLogic, stage);

		gridActor = new GridActor(gameLogic, 280, 325, 70, 64);
		NextPieceActor nextPieceActor = new NextPieceActor(gameLogic, 75, 920, 64);
		ScoreActor scoreActor = new ScoreActor(gameLogic, 590, 1230);
		
		TextureRegion pauseRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/pause_button.png",Texture.class), 32, 32);
		stage.addActor(new BackgroundActor(ScreenOrientation.PORTRAIT));
		stage.addActor(new PortraitPanelActor());
		stage.addActor(gridActor);
		stage.addActor(nextPieceActor);
		stage.addActor(scoreActor);

		controllerActor = new ControlerActor(ScreenOrientation.PORTRAIT, gameLogic);
		stage.addActor(controllerActor);
		
		pauseButton = new PauseButton(pauseRegion);
		addButton(pauseButton,10,VIRTUAL_HEIGHT-10-pauseRegion.getRegionHeight());
		addButton(MusicButtonActor.createMusicButton(),VIRTUAL_WIDTH-42, VIRTUAL_HEIGHT-42);
		
		pauseMenu = new PauseMenu(this, ScreenOrientation.PORTRAIT);
		stage.addActor(pauseMenu);
		
		stage.getCamera().rotate(-90, 0, 0, 1);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	protected boolean gameEnded() {
		return gameLogic.getState() == State.LOST;
	}
	
	@Override
	public void render(float delta) {
		if (!gameEnded()) {
			if (!gamePaused) {
				this.elapsedTime += delta;
			}
			// Update model
			gameLogic.update(delta);
		} else {
			Gdx.input.setInputProcessor(null);
			end = true;
			// TODO
		}
		
		draw(delta);
	}
	
	private void draw(float delta) {
		GLCommon gl = Gdx.gl;
		
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int arg0, int arg1) {
		stage.setViewport(VIRTUAL_HEIGHT, VIRTUAL_WIDTH, false);
		stage.getCamera().position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller );
	}

	public float getElapsedTime() {
		return elapsedTime;
	}

	@Override
	public void init() {
		gameLogic.init();
		elapsedTime = 0;
	}

	@Override
	public void gamePause() {
		gamePaused = true;
		gameLogic.pause();
		controllerActor.touchable = false;
		pauseButton.touchable = false;
	}

	@Override
	public void gameResume() {
		gamePaused = false;
		gameLogic.resume();
		controllerActor.touchable = true;
		pauseButton.touchable = true;
	}
	
}
