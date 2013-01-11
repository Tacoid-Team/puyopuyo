package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.pweek.logic.GameLogic;

public class ScoreActor extends Actor {
	private GameLogic logic;
	private BitmapFont font;
	private int origX;
	private int origY;
	

	public ScoreActor(BitmapFont font, GameLogic logic, int origX, int origY) {
		this.logic = logic;
		this.origX = origX;
		this.origY = origY;
		// A commenter pour le porting gwt
		this.font = font;
		font.setColor(1f, 1f, 1f, 1f);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		String score = String.valueOf(logic.getScore());
		font.setColor(1f, 1f, 1f, 1f);
		font.setScale(1.0f);
		font.draw(batch, score, origX - font.getBounds(score).width, origY);

		/*
		long date = System.currentTimeMillis();
		int offset = -30;
		for (Explosion e : logic.getExplosions()) {
			score = String.valueOf("+"+e.points+"!");
			
			float alphaFont;
			if (date - e.getExplosionDate() < 100) {
				alphaFont = Interpolation.linear.apply(0, 1, (date - e.getExplosionDate()) / 100.0f);
			} else if (date - e.getExplosionDate() > 700) {
				alphaFont = Interpolation.linear.apply(1, 0, (date - e.getExplosionDate() - 700) / 300.0f);
			} else {
				alphaFont = 1;
			}
			
			font.setColor(0.0f, 0.0f, 0.0f, 0.8f * alphaFont);
			font.draw(batch, score, origX - font.getBounds(score).width + 2, origY + 2 + offset);
			font.setColor(1.0f, 1.0f, 1.0f, 1f * alphaFont);
			font.draw(batch, score, origX - font.getBounds(score).width, origY + offset);
			
			offset -= font.getLineHeight() - 2;
		}*/
	}

	@Override
	public Actor hit(float arg0, float arg1, boolean touchable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
