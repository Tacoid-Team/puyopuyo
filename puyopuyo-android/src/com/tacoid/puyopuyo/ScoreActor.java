package com.tacoid.puyopuyo;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.logic.GameLogic;

public class ScoreActor extends Actor {
	private GameLogic logic;
	private BitmapFont font;
	

	public ScoreActor(GameLogic logic) {
		this.logic = logic;
		font = new BitmapFont();
		font.setScale(1.2f);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		String score = String.valueOf(logic.getScore());
		font.draw(batch, score, 280 - font.getBounds(String.valueOf(logic.getScore())).width, 430);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
