package com.tacoid.puyopuyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tacoid.puyopuyo.logic.GameLogic;

public class GameScreen implements Screen {
	private static final int VIRTUAL_WIDTH = 675;
	private static final int VIRTUAL_HEIGHT = 450;
	private static GameScreen instance = null;
	private Stage stage;
	private GameLogic gameLogic;
	private GameLogic gameLogicIA;
	private GridActor gridActor;
	private GridActor gridActorIA;
	
	private class DownButton extends Button {
		
		public DownButton(TextureRegion regionUp, TextureRegion regionDown) {
			super(regionUp, regionDown);
			x = 29;
			y = 5;
		}
		
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			super.touchDown(x, y, pointer);
			gameLogic.down();
			return true;
		}
		
		@Override
		public void touchUp(float x, float y, int pointer) {
			super.touchUp(x, y, pointer);
			gameLogic.up();
		}
	}
	
	
	private class LeftButton extends Button {
		
		public LeftButton(TextureRegion regionUp, TextureRegion regionDown) {
			super(regionUp, regionDown);
			x = 29;
			y = 85;
		}
		
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			super.touchDown(x, y, pointer);
			gameLogic.moveLeft();
			return true;
		}
	}
	
	private class RotateLeftButton extends Button {
		
		public RotateLeftButton(TextureRegion regionUp, TextureRegion regionDown) {
			super(regionUp, regionDown);
			x = 29;
			y = 162;
		}
		
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			super.touchDown(x, y, pointer);
			gameLogic.rotateLeft();
			return true;
		}	
	}

	private class RotateRightButton extends Button {
		
		public RotateRightButton(TextureRegion regionUp, TextureRegion regionDown) {
			super(regionUp, regionDown);
			x = 570;
			y = 162;
		}
		
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			super.touchDown(x, y, pointer);
			gameLogic.rotateRight();
			return true;
		}
	}

	
	private class RightButton extends Button {
		
		public RightButton(TextureRegion regionUp, TextureRegion regionDown) {
			super(regionUp, regionDown);
			x = 570;
			y = 85;
		}
				
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			super.touchDown(x, y, pointer);
			gameLogic.moveRight();
			return true;
		}
		
	}

	private GameScreen() {
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		gameLogic = new GameLogic();
		gameLogicIA = new GameLogic();

		gridActor = new GridActor(gameLogic, 124, 16);
		NextPieceActor nextPieceActor = new NextPieceActor(gameLogic, 30, 330);
		ScoreActor scoreActor = new ScoreActor(gameLogic, 280, 430);
		
		gridActorIA = new GridActor(gameLogicIA, 349, 16);
		NextPieceActor nextPieceActorIA = new NextPieceActor(gameLogicIA, 570, 330);
		ScoreActor scoreActorIA = new ScoreActor(gameLogicIA, 445, 430);
		
		TextureRegion backgroundRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/fond.png", Texture.class), VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		Image background = new Image(backgroundRegion);
		
		stage.addActor(background);
		stage.addActor(gridActor);
		stage.addActor(nextPieceActor);
		stage.addActor(scoreActor);
		stage.addActor(gridActorIA);
		stage.addActor(nextPieceActorIA);
		stage.addActor(scoreActorIA);
		
		TextureRegion leftRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/left.png", Texture.class), 80, 80);
		TextureRegion leftDownRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/left_down.png", Texture.class), 80, 80);
		stage.addActor(new LeftButton(leftRegion, leftDownRegion));
		
		TextureRegion rightRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/right.png", Texture.class), 80, 80);
		TextureRegion rightDownRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/right_down.png", Texture.class), 80, 80);
		stage.addActor(new RightButton(rightRegion, rightDownRegion));

		TextureRegion rotleftRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotleft.png", Texture.class), 80, 80);
		TextureRegion rotleftDownRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotleft_down.png", Texture.class), 80, 80);
		stage.addActor(new RotateLeftButton(rotleftRegion, rotleftDownRegion));
		
		TextureRegion rotrightRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotright.png", Texture.class), 80, 80);
		TextureRegion rotrightDownRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/rotright_down.png", Texture.class), 80, 80);
		stage.addActor(new RotateRightButton(rotrightRegion, rotrightDownRegion));

		TextureRegion downRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/down.png", Texture.class), 80, 80);
		TextureRegion downDownRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/down_down.png", Texture.class), 80, 80);
		stage.addActor(new DownButton(downRegion, downDownRegion));
	}
	
	public static GameScreen getInstance() {
		if (instance == null) {
			instance = new GameScreen();
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
		GL10 gl = Gdx.graphics.getGL10();

		// Update model
		gameLogic.update(delta);
		gameLogicIA.update(delta);

		// clear previous frame
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int arg0, int arg1) {
		stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		stage.getCamera().position.set(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, 0);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

}
