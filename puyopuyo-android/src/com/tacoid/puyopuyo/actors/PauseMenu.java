package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.tacoid.puyopuyo.MainMenuScreen;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.logic.GameLogic;

public class PauseMenu extends Group{
	
	private float time;
	private GameLogic playerLogic;
	private GameLogic iaLogic;
	
	
	private class ContinueButton extends Button{
		public ContinueButton(TextureRegion region) {
			super(region);
			x = 100;
		}

		public boolean touchDown(float x, float y, int pointer) {
			hide();
			return true;
		}
	}
	
	private class QuitButton extends Button{
		public QuitButton(TextureRegion region) {
			super(region);
			x = 800;
		}

		public boolean touchDown(float x, float y, int pointer) {
			playerLogic.init();
			if(iaLogic != null) {
				iaLogic.init();
			}
			hide();
			PuyoPuyo.getInstance().setScreen(MainMenuScreen.getInstance());
			return true;
		}
	}
	
	private class ForegroundActor extends Actor {
		private Texture ForegroundTex;
		private Interpolation interp;
		
		public ForegroundActor() {
			ForegroundTex = new Texture(Gdx.files.internal("images/menu/foreground.png"));
			interp = new Interpolation.Pow(2);
		}
		
		@Override
		public void draw(SpriteBatch batch, float delta) {
		     time+=Gdx.graphics.getDeltaTime()*0.5;
		     if(time> 1.0f) {
		    	 time = 1.0f;
		     }
			batch.draw(ForegroundTex,0,interp.apply(-ForegroundTex.getHeight(),0.0f,time));
		}

		@Override
		public Actor hit(float arg0, float arg1) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public PauseMenu(GameLogic player, GameLogic ia) {
		TextureRegion continueRegion = new TextureRegion(new TextureRegion(PuyoPuyo.getInstance().manager.get("images/continuer-fr.png",Texture.class)));
		TextureRegion quitRegion = new TextureRegion(new TextureRegion(PuyoPuyo.getInstance().manager.get("images/quitter-fr.png",Texture.class)));
		
		this.addActor(new ForegroundActor());
		this.addActor(new ContinueButton(continueRegion));
		this.addActor(new QuitButton(quitRegion));
		
		playerLogic = player;
		iaLogic = ia;
		visible = false;
	}
	
	public void show() {
		time = 0.5f;
		playerLogic.pause();
		
		if(iaLogic != null) {
			iaLogic.pause();
		}
		this.visible = true;
	}
	
	public void hide() {
		playerLogic.resume();
		
		if(iaLogic != null) {
			iaLogic.resume();
		}
		this.visible = false;
	}
}
