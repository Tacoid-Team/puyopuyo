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

		TextureRegion leftRegion = PuyoPuyo.getInstance().atlasControls.findRegion("left");
		TextureRegion leftDownRegion =  PuyoPuyo.getInstance().atlasControls.findRegion("left_down");
		TextureRegion rightRegion =  PuyoPuyo.getInstance().atlasControls.findRegion("right");
		TextureRegion rightDownRegion =  PuyoPuyo.getInstance().atlasControls.findRegion("right_down");
		TextureRegion rotleftRegion =  PuyoPuyo.getInstance().atlasControls.findRegion("rotleft");
		TextureRegion rotleftDownRegion = PuyoPuyo.getInstance().atlasControls.findRegion("rotleft_down");
		TextureRegion rotrightRegion = PuyoPuyo.getInstance().atlasControls.findRegion("rotright");
		TextureRegion rotrightDownRegion = PuyoPuyo.getInstance().atlasControls.findRegion("rotright_down");
		TextureRegion downRegion = PuyoPuyo.getInstance().atlasControls.findRegion("down");
		TextureRegion downDownRegion = PuyoPuyo.getInstance().atlasControls.findRegion("down_down");

		TextureRegion backgroundRegion 	= new TextureRegion(PuyoPuyo.getInstance().manager.get("images/fond_solo.png",Texture.class), VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		Image background = new Image(backgroundRegion);

		stage.addActor(background);
		stage.addActor(gridActor);
		stage.addActor(nextPieceActor);
		stage.addActor(scoreActor);

		stage.addActor(new MoveButton(MoveType.LEFT, gameLogic, 29, 10, leftRegion, leftDownRegion));
		stage.addActor(new MoveButton(MoveType.RIGHT, gameLogic, 570, 10, rightRegion, rightDownRegion));
		stage.addActor(new MoveButton(MoveType.ROT_LEFT, gameLogic, 29, 162, rotleftRegion, rotleftDownRegion));
		stage.addActor(new MoveButton(MoveType.ROT_RIGHT, gameLogic, 570, 162, rotrightRegion, rotrightDownRegion));
		stage.addActor(new MoveButton(MoveType.DOWN, gameLogic, 200, 5, downRegion, downDownRegion));
		
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
