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
import com.tacoid.puyopuyo.actors.GameOverActor;
import com.tacoid.puyopuyo.actors.GridActor;
import com.tacoid.puyopuyo.actors.MoveButton;
import com.tacoid.puyopuyo.actors.NextPieceActor;
import com.tacoid.puyopuyo.actors.PauseMenu;
import com.tacoid.puyopuyo.actors.ScoreActor;
import com.tacoid.puyopuyo.actors.GameOverActor.GameOverType;
import com.tacoid.puyopuyo.logic.GameLogic;
import com.tacoid.puyopuyo.logic.IA;
import com.tacoid.puyopuyo.logic.State;
import com.tacoid.puyopuyo.logic.GameLogic.MoveType;

public class GameScreen implements Screen {
	private static final int VIRTUAL_WIDTH = 1280;
	private static final int VIRTUAL_HEIGHT = 768;
	private static GameScreen instance = null;
	private Stage stage;
	private GameLogic gameLogic;
	private GameLogic gameLogicIA;
	private GridActor gridActor;
	private GridActor gridActorIA;
	private IA ia;
	private InputProcessor controller;
	private PauseMenu pauseMenu;

	private void addButton(Button button, int x, int y) {
		stage.addActor(button);
		button.x = x;
		button.y = y;
	}
	
	private class PauseButton extends Button {

		public PauseButton(TextureRegion region) {
			super(region);
		}
		
		public boolean touchDown(float x, float y, int pointer) {
			pauseMenu.show();
			return true;
		}

	}


	private GameScreen() {
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		gameLogic = new GameLogic();
		gameLogicIA = new GameLogic();
		gameLogic.setOpponent(gameLogicIA);
		gameLogicIA.setOpponent(gameLogic);
		controller = new Controller(gameLogic, stage);

		gridActor = new GridActor(gameLogic, 214, 26, 54, 48);
		NextPieceActor nextPieceActor = new NextPieceActor(gameLogic, 55, 480, 48);
		ScoreActor scoreActor = new ScoreActor(gameLogic, 480, 730);

		gridActorIA = new GridActor(gameLogicIA, 728, 26, 54, 48);
		NextPieceActor nextPieceActorIA = new NextPieceActor(gameLogicIA, 1100,	480, 48);
		ScoreActor scoreActorIA = new ScoreActor(gameLogicIA, 890, 730);

		TextureRegion backgroundRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/fond.png",Texture.class), VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		TextureRegion leftRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/left.png",Texture.class), 80, 80);
		TextureRegion leftDownRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/left_down.png",Texture.class), 80, 80);
		TextureRegion rightRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/right.png",Texture.class), 80, 80);
		TextureRegion rightDownRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/right_down.png",Texture.class), 80, 80);
		TextureRegion rotleftRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotleft.png",Texture.class), 80, 80);
		TextureRegion rotleftDownRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotleft_down.png",Texture.class), 80, 80);
		TextureRegion rotrightRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotright.png",Texture.class), 80, 80);
		TextureRegion rotrightDownRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotright_down.png",Texture.class), 80, 80);
		TextureRegion downRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/down.png",Texture.class), 80, 80);
		TextureRegion downDownRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/down_down.png",Texture.class), 80, 80);
		TextureRegion pauseRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/pause_button.png",Texture.class), 32, 32);

		Image background = new Image(backgroundRegion);

		stage.addActor(background);
		stage.addActor(gridActor);
		stage.addActor(nextPieceActor);
		stage.addActor(scoreActor);
		stage.addActor(gridActorIA);
		stage.addActor(nextPieceActorIA);
		stage.addActor(scoreActorIA);

		stage.addActor(new MoveButton(MoveType.LEFT, gameLogic, 70, 250, leftRegion, leftDownRegion));
		stage.addActor(new MoveButton(MoveType.RIGHT, gameLogic, 1120, 250, rightRegion, rightDownRegion));
		stage.addActor(new MoveButton(MoveType.ROT_LEFT, gameLogic, 70, 150, rotleftRegion, rotleftDownRegion));
		stage.addActor(new MoveButton(MoveType.ROT_RIGHT, gameLogic, 1120, 150, rotrightRegion, rotrightDownRegion));
		stage.addActor(new MoveButton(MoveType.DOWN, gameLogic, 70, 50, downRegion, downDownRegion));
		
		addButton(new PauseButton(pauseRegion),10,VIRTUAL_HEIGHT-10-pauseRegion.getRegionHeight());
		
		pauseMenu = new PauseMenu(gameLogic, gameLogicIA);
		stage.addActor(pauseMenu);

		ia = new IA(gameLogicIA);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1.0f);
	}

	public static GameScreen getInstance() {
		if (instance == null) {
			instance = new GameScreen();
		}
		return instance;
	}
	
	public static void freeInstance() {
		instance = null;
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

		if (gameLogic.getState() != State.LOST
				&& gameLogicIA.getState() != State.LOST) {
			// Update model
			gameLogic.update(delta);
			gameLogicIA.update(delta);
			ia.update(delta);
		} else {
			if(gameLogic.getState() == State.LOST){
				stage.addActor(new GameOverActor(GameOverType.LOSE, VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2));
			} else {
				stage.addActor(new GameOverActor(GameOverType.WIN, VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2));
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
		stage.getCamera().position
				.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
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
