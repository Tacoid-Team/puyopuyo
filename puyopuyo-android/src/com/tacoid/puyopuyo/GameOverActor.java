package com.tacoid.puyopuyo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameOverActor extends Actor {
	
	static public enum GameOverType {
		WIN,
		LOSE,
		GAMEOVER
	};

	private SpriteBatch batch;
	private Sprite sprite;
	private GameOverType type;
	
	public GameOverActor(GameOverType type, float x, float y) {
		this.type = type;
		batch = new SpriteBatch();
		
		switch(type) {
		case WIN:
			sprite = new Sprite(PuyoPuyo.getInstance().manager.get("images/gagne-fr.png", Texture.class));
			break;
		case LOSE:
			sprite = new Sprite(PuyoPuyo.getInstance().manager.get("images/perdu-fr.png", Texture.class));
			break;
		default:
			sprite = new Sprite(PuyoPuyo.getInstance().manager.get("images/perdu-fr.png", Texture.class));
		}
		
		this.x = x;
		this.y = y;
		sprite.setPosition(x-sprite.getWidth()/2, y-sprite.getHeight()/2);
	}
	
	@Override
	public void draw(SpriteBatch arg0, float arg1) {
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return this;
	}
	
	public boolean touchDown(float x, float y, int pointer) {
		PuyoPuyo.getInstance().setScreen(MainMenuScreen.getInstance());
		GameScreen.freeInstance();
		return true;
	}
	
	public void touchUp(float x, float y, int pointer) {
		PuyoPuyo.getInstance().setScreen(MainMenuScreen.getInstance());
		GameScreen.freeInstance();
	}
	
	
}
