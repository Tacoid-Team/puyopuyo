package com.tacoid.puyopuyo;

import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tacoid.puyopuyo.actors.GridActor;
import com.tacoid.puyopuyo.actors.MoveButton;
import com.tacoid.puyopuyo.actors.NextPieceActor;
import com.tacoid.puyopuyo.actors.ScoreActor;
import com.tacoid.puyopuyo.logic.GameLogic;
import com.tacoid.puyopuyo.logic.GameLogic.MoveType;
import com.tacoid.puyopuyo.logic.State;

public class GameSoloScreen implements Screen {
	private static final int VIRTUAL_WIDTH = 768;
	private static final int VIRTUAL_HEIGHT = 1280;
	private static GameSoloScreen instance = null;
	protected Stage stage;
	protected GameLogic gameLogic;
	private GridActor gridActor;
	private InputProcessor controller;
	protected boolean end = false;

	protected GameSoloScreen() {
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		gameLogic = new GameLogic();
		controller = new Controller(gameLogic, stage);

		gridActor = new GridActor(gameLogic, 280, 320, 75, 70);
		NextPieceActor nextPieceActor = new NextPieceActor(gameLogic, 80, 920, 70);
		ScoreActor scoreActor = new ScoreActor(gameLogic, 590, 1230);

		TextureRegion backgroundRegion 	= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/fond_solo.png",Texture.class), VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		TextureRegion leftRegion		= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/left.png",Texture.class), 80, 80);
		TextureRegion leftDownRegion 	= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/left_down.png",Texture.class), 80, 80);
		TextureRegion rightRegion 		= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/right.png",Texture.class), 80, 80);
		TextureRegion rightDownRegion 	= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/right_down.png",Texture.class), 80, 80);
		TextureRegion rotleftRegion 	= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotleft.png",Texture.class), 80, 80);
		TextureRegion rotleftDownRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotleft_down.png",Texture.class), 80, 80);
		TextureRegion rotrightRegion 	= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotright.png",Texture.class), 80, 80);
		TextureRegion rotrightDownRegion= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotright_down.png",Texture.class), 80, 80);
		TextureRegion downRegion 		= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/down.png",Texture.class), 80, 80);
		TextureRegion downDownRegion 	= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/down_down.png",Texture.class), 80, 80);
		Image background = new Image(backgroundRegion);

		stage.addActor(background);
		stage.addActor(gridActor);
		stage.addActor(nextPieceActor);
		stage.addActor(scoreActor);

		stage.addActor(new MoveButton(MoveType.LEFT, gameLogic, 29, 85, leftRegion, leftDownRegion));
		stage.addActor(new MoveButton(MoveType.RIGHT, gameLogic, 570, 85, rightRegion, rightDownRegion));
		stage.addActor(new MoveButton(MoveType.ROT_LEFT, gameLogic, 29, 162, rotleftRegion, rotleftDownRegion));
		stage.addActor(new MoveButton(MoveType.ROT_RIGHT, gameLogic, 570, 162, rotrightRegion, rotrightDownRegion));
		stage.addActor(new MoveButton(MoveType.DOWN, gameLogic, 29, 5, downRegion, downDownRegion));
		
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

	@Override
	public void render(float delta) {
		GLCommon gl = Gdx.gl;

		if (gameLogic.getState() != State.LOST) {
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
		stage.getCamera().rotate(-90, 0, 0, 1);
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
