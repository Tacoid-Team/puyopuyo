package com.tacoid.puyopuyo;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;
import com.tacoid.puyopuyo.actors.BackgroundActor;

public class LoadingScreen implements Screen {
	private static final int VIRTUAL_WIDTH = 1280;
	private static final int VIRTUAL_HEIGHT = 768;
	
	private static LoadingScreen instance = null;
	public static boolean initialized;
	private PuyoPuyo puyopuyo;
	private BitmapFont font;
	private Stage stage;
	
	private Sprite logoSprite;
	
	private class loadingActor extends Actor {
		private float time = 0.0f;
		@Override
		public void draw(SpriteBatch batch, float delta) {
			NumberFormat nf = new DecimalFormat("0.00"); 
			String loadMsg = "Loading " + nf.format(puyopuyo.manager.getProgress() * 100) + "%";
			font.draw(batch, loadMsg, PuyoPuyo.VIRTUAL_WIDTH / 2 - font.getBounds(loadMsg).width / 2, 20);
			time+=delta;
			logoSprite.setColor(Math.min(time/20.0f, 1.0f), Math.min(time/20.0f, 1.0f), Math.min(time/20.0f, 1.0f), Math.min(time/20.0f, 1.0f));
			logoSprite.draw(batch);
		}

		@Override
		public Actor hit(float arg0, float arg1) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public void init() {
		this.puyopuyo = PuyoPuyo.getInstance();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		this.font = new BitmapFont();
		font.setScale(1.0f);
		this.logoSprite = new Sprite(new Texture(Gdx.files.internal("images/menu/logo.png")));
		this.logoSprite.setPosition(VIRTUAL_WIDTH/2 - logoSprite.getWidth()/2, VIRTUAL_HEIGHT/2 - logoSprite.getHeight()/2);
		stage.addActor(new loadingActor());
		initialized = true;
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
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if (puyopuyo.manager.update()) {
			puyopuyo.setScreen(MainMenuScreen.getInstance());
		}
		// Non compatible gwt.
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		stage.draw();
		
		stage.act(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int arg0, int arg1) {
		stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		stage.getCamera().position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
	}
	public static LoadingScreen getInstance() {
		if (instance == null) {
			instance = new LoadingScreen();
		}
		if (!initialized) {
			instance.init();
		}
		return instance;
	}

}
