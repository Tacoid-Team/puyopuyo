package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.tacoid.puyopuyo.GameScreen;
import com.tacoid.puyopuyo.PuyoPuyo;

public class StartActor extends Actor {
	
	GameScreen gameScreen;
	
	Sprite touchSprite;
	
	public StartActor(GameScreen screen) {
		touchSprite = new Sprite(PuyoPuyo.getInstance().atlasPlank.findRegion("touchtostart"));
		gameScreen = screen;
		switch(screen.getOrientation()) {
		case LANDSCAPE:
			touchSprite.setPosition(screen.getWidth()/2.0f - touchSprite.getWidth()/2, screen.getHeight()/2.0f);
			break;
		case PORTRAIT:
			touchSprite.setPosition(screen.getWidth()/2.0f - touchSprite.getWidth()/2, 2.0f*screen.getHeight()/5.0f);
			touchSprite.setScale(0.8f);
			break;
		}
	}

	@Override
	public void draw(SpriteBatch batch, float arg1) {
			touchSprite.draw(batch);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		return this;
	}
	
	public void hide() {
		gameScreen.gameResume();
		this.visible = false;
		this.touchable = false;
	}
	
	public void show() {
		gameScreen.gamePause();
		this.visible = true;
		this.touchable = true;
	}
	
	public boolean touchDown(float x, float y, int pointer) {
		return true;
	}
	
	public void touchUp(float x, float y, int pointer) {
		this.hide();
	}
}
