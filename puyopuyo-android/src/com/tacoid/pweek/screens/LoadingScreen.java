package com.tacoid.pweek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tacoid.pweek.Pweek;

public class LoadingScreen implements Screen {
	private static final int VIRTUAL_WIDTH = 1280;
	private static final int VIRTUAL_HEIGHT = 768;
	
	private static LoadingScreen instance = null;
	public static boolean initialized;
	private Pweek puyopuyo;
	private Stage stage;
	
	private Sprite logoSprite;
	NinePatch patch;
	
	private class loadingActor extends Actor {

		private float percent = 0;
		
		@Override
		public void draw(SpriteBatch batch, float delta) {
			logoSprite.draw(batch);
			// C'est pas terrible mais ça adoucit un peu la barre de progression quand même.
			percent = Interpolation.linear.apply(percent, puyopuyo.manager.getProgress(), 0.5f);
			
			patch.draw(batch, 32,
							  32,
							  Math.max(percent * (VIRTUAL_WIDTH-64),10), 
							  32);
			
		}

		@Override
		public Actor hit(float arg0, float arg1, boolean touchable) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public void init() {
		this.puyopuyo = Pweek.getInstance();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		patch = new NinePatch(new Texture(Gdx.files.internal("images/menu/progress-patch.png")), 12, 12, 12, 12);
		this.logoSprite = new Sprite(new Texture(Gdx.files.internal("images/menu/logo.png")));
		this.logoSprite.setPosition(VIRTUAL_WIDTH/2 - logoSprite.getWidth()/2, VIRTUAL_HEIGHT/2 - logoSprite.getHeight()/2);
		stage.addActor(new loadingActor());
	}
	
	@Override
	public void dispose() {
		Gdx.app.log("Loading", "paused");
		
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
		resize(0,0);
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
