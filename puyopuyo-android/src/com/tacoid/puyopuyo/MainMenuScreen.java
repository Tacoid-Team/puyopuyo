package com.tacoid.puyopuyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;


public class MainMenuScreen implements Screen {
	private static final int VIRTUAL_WIDTH = 1280;
	private static final int VIRTUAL_HEIGHT = 768;
	private static MainMenuScreen instance = null;
	private Stage stage;
	private SpriteBatch batch;
	
	float scrollTimer = 0.0f;
	float foregroundTimer = 0.0f;
	
	Texture SkyTex;
    Sprite SkySprite;
    
	Texture HillsTex;
	Texture ForegroundTex;
	
	public MainMenuScreen() {
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		
		SkyTex = new Texture(Gdx.files.internal("images/menu/sky.png"));
		SkyTex.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		SkySprite = new Sprite(SkyTex, 0,256,VIRTUAL_WIDTH, VIRTUAL_HEIGHT+256);
		SkySprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		HillsTex = new Texture(Gdx.files.internal("images/menu/hills.png"));
		ForegroundTex = new Texture(Gdx.files.internal("images/menu/foreground.png"));
		batch = new SpriteBatch();
		
		TextureRegion playRegion = new TextureRegion(
				PuyoPuyo.getInstance().manager.get("images/menu/play.png",
						Texture.class), 100, 100);
		TextureRegion playDownRegion = new TextureRegion(
				PuyoPuyo.getInstance().manager.get("images/menu/play.png",
						Texture.class), 100, 100);
		stage.addActor(new PlayButton(playRegion, playDownRegion));

	}
	
	

	static public MainMenuScreen getInstance()
	{
			if (instance == null) {
				instance = new MainMenuScreen();
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
	public void render(float arg0) {
		
		scrollTimer+=Gdx.graphics.getDeltaTime()*0.01;
	     if(scrollTimer>1.0f)
	         scrollTimer = 0.0f;
		
	     foregroundTimer+=Gdx.graphics.getDeltaTime()*0.5;
	     if(foregroundTimer> 1.0f) {
	    	 foregroundTimer = 1.0f;
	     }
	     
	     //Gdx.gl.glClearColor(1.0f, 0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		SkySprite.setU(scrollTimer);
		SkySprite.setU2(scrollTimer+1);
		batch.begin();
		SkySprite.draw(batch);
		batch.draw(HillsTex,0,0);
		batch.draw(ForegroundTex,0,1000*-(foregroundTimer/2 - 0.5f)*(foregroundTimer/2 - 0.5f));
		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
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
		Gdx.input.setInputProcessor(stage);
		
	}
	

	private class PlayButton extends Button{
		public PlayButton(TextureRegion regionUp, TextureRegion regionDown) {
			super(regionUp, regionDown);
			x = 100;
			y = 100;
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean touchDown(float x, float y, int pointer) {
			PuyoPuyo.getInstance().setScreen(GameScreen.getInstance());
			return true;
		}
		public void touchUp(float x, float y, int pointer) {
			PuyoPuyo.getInstance().setScreen(GameScreen.getInstance());
		}
	}

}