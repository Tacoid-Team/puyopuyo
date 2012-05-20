package com.tacoid.puyopuyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tacoid.puyopuyo.logic.GameLogic;

public class GameScreen implements Screen {
	private static final int VIRTUAL_WIDTH = 675;
	private static final int VIRTUAL_HEIGHT = 450;
	private static GameScreen instance = null;
	private Stage stage;
	private GameLogic gameLogic;
	private GridActor gridActor;
	
	private class DownButton extends Actor {

		private Texture down;
		
		public DownButton() {
			down = PuyoPuyo.getInstance().manager.get("images/down.png", Texture.class);
			x = 29;
			y = 5;
			width = 64;
			height = 64;
			touchable = true;
		}
		
		@Override
		public void draw(SpriteBatch batch, float alpha) {
			batch.draw(down, x, y);
		}

		@Override
		public Actor hit(float x, float y) {
			return x < width && x > 0 && y < height && y > 0 ? this : null;
		}
		
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			gameLogic.moveDown();
			return true;
		}	
	}
	
	
	private class LeftButton extends Actor {

		private Texture left;
		
		public LeftButton() {
			left = PuyoPuyo.getInstance().manager.get("images/left.png", Texture.class);
			x = 29;
			y = 95;
			width = 64;
			height = 64;
			touchable = true;
		}
		
		@Override
		public void draw(SpriteBatch batch, float alpha) {
			batch.draw(left, x, y);
		}

		@Override
		public Actor hit(float x, float y) {
			return x < width && x > 0 && y < height && y > 0 ? this : null;
		}
		
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			gameLogic.moveLeft();
			return true;
		}	
	}
	
	private class RotateLeftButton extends Actor {

		private Texture left;
		
		public RotateLeftButton() {
			left = PuyoPuyo.getInstance().manager.get("images/rotleft.png", Texture.class);
			x = 29;
			y = 182;
			width = 64;
			height = 64;
			touchable = true;
		}
		
		@Override
		public void draw(SpriteBatch batch, float alpha) {
			batch.draw(left, x, y);
		}

		@Override
		public Actor hit(float x, float y) {
			return x < width && x > 0 && y < height && y > 0 ? this : null;
		}
		
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			gameLogic.rotateLeft();
			return true;
		}	
	}

	private class RotateRightButton extends Actor {

		private Texture right;
		
		public RotateRightButton() {
			right = PuyoPuyo.getInstance().manager.get("images/rotright.png", Texture.class);
			x = 570;
			y = 182;
			width = 64;
			height = 64;
			touchable = true;
		}
		
		@Override
		public void draw(SpriteBatch batch, float alpha) {
			batch.draw(right, x, y);
		}

		@Override
		public Actor hit(float x, float y) {
			return x < width && x > 0 && y < height && y > 0 ? this : null;
		}
		
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			gameLogic.rotateRight();
			return true;
		}	
	}

	
	private class RightButton extends Actor {

		private Texture left;
		
		public RightButton() {
			left = PuyoPuyo.getInstance().manager.get("images/right.png", Texture.class);
			x = 570;
			y = 95;
			width = 64;
			height = 64;
			touchable = true;
		}
		
		@Override
		public void draw(SpriteBatch batch, float alpha) {
			batch.draw(left, x, y);
		}

		@Override
		public Actor hit(float x, float y) {
			return x < width && x > 0 && y < height && y > 0 ? this : null;
		}
		
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			gameLogic.moveRight();
			return true;
		}
		
	}

	private GameScreen() {
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		gameLogic = new GameLogic();

		gridActor = new GridActor(gameLogic);
		NextPieceActor nextPieceActor = new NextPieceActor(gameLogic);
		ScoreActor scoreActor = new ScoreActor(gameLogic);
		
		TextureRegion backgroundRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/fond.png", Texture.class), VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		Image background = new Image(backgroundRegion);
		
		stage.addActor(background);
		stage.addActor(gridActor);
		stage.addActor(nextPieceActor);
		stage.addActor(scoreActor);
		stage.addActor(new LeftButton());
		stage.addActor(new RightButton());
		stage.addActor(new RotateLeftButton());
		stage.addActor(new RotateRightButton());
		stage.addActor(new DownButton());
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
