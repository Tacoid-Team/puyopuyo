package com.tacoid.puyopuyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;
import com.tacoid.puyopuyo.actors.ControlerActor;
import com.tacoid.puyopuyo.actors.GridActor;
import com.tacoid.puyopuyo.actors.NextPieceActor;
import com.tacoid.puyopuyo.actors.ScoreActor;
import com.tacoid.puyopuyo.actors.ControlerActor.ControlerLayout;
import com.tacoid.puyopuyo.logic.GameLogic;
import com.tacoid.puyopuyo.logic.State;

public class GameSoloScreen implements Screen {
	private static final int VIRTUAL_WIDTH = 1280;
	private static final int VIRTUAL_HEIGHT = 768;
	private static GameSoloScreen instance = null;
	protected Stage stage;
	protected GameLogic gameLogic;
	private GridActor gridActor;
	private InputProcessor controller;
	protected boolean end = false;
	protected float elapsedTime;

	protected GameSoloScreen() {
		elapsedTime = 0;
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		gameLogic = new GameLogic();
		controller = new Controller(gameLogic, stage);

		gridActor = new GridActor(gameLogic, 275, 330, 70, 64);
		NextPieceActor nextPieceActor = new NextPieceActor(gameLogic, 75, 920, 64);
		ScoreActor scoreActor = new ScoreActor(gameLogic, 590, 1230);

		TextureRegion backgroundRegion 	= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/fond_solo.png",Texture.class), VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		Image background = new Image(backgroundRegion);

		stage.addActor(background);
		stage.addActor(gridActor);
		stage.addActor(nextPieceActor);
		stage.addActor(scoreActor);

		stage.addActor(new ControlerActor(ControlerLayout.CLASSIC, ScreenOrientation.PORTRAIT, gameLogic));
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1.0f);
	}

	public static GameSoloScreen getInstance() {
		if (instance == null) {
			instance = new GameSoloScreen();
		}
		return instance;
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
		this.elapsedTime += delta;
		GLCommon gl = Gdx.gl;

		if (!gameEnded()) {
			// Update model
			gameLogic.update(delta);
		} else {
			Gdx.input.setInputProcessor(null);
			end = true;
			// TODO
		}

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

}
