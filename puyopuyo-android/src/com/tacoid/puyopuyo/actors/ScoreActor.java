package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.logic.GameLogic;

public class ScoreActor extends Actor {
	private GameLogic logic;
	private BitmapFont font;
	private int origX;
	private int origY;
	

	public ScoreActor(GameLogic logic, int origX, int origY) {
		this.logic = logic;
		this.origX = origX;
		this.origY = origY;
		// A commenter pour le porting gwt
		font = PuyoPuyo.getInstance().manager.get("images/font_score.fnt", BitmapFont.class);
		font.setColor(1f, 1f, 1f, 1f);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		String score = String.valueOf(logic.getScore());
		font.setColor(1f, 1f, 1f, 1f);
		font.draw(batch, score, origX - font.getBounds(score).width, origY);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
