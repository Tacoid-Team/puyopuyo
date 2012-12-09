package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.screens.GameScreen;

public class StartActor extends Actor {
	
	GameScreen gameScreen;
	
	Sprite touchSprite;
	
	public StartActor(GameScreen screen) {
		touchSprite = new Sprite(Pweek.getInstance().atlasPlank.findRegion("touchtostart"));
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
		
		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				hide();
			}
		});
	}

	@Override
	public void draw(SpriteBatch batch, float arg1) {
			touchSprite.draw(batch);
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		return this;
	}
	
	public void hide() {
		gameScreen.gameResume();
		setVisible(false);
		setTouchable(Touchable.disabled);
	}
	
	public void show() {
		gameScreen.gamePause();
		setVisible(true);
		setTouchable(Touchable.enabled);
	}
}
