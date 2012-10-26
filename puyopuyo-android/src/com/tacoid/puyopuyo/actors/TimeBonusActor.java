package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.I18nManager;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.screens.GameVersusScreen;

public class TimeBonusActor extends Actor {
	private GameVersusScreen screen;
	private BitmapFont font;
	private int origX;
	private int origY;
	

	public TimeBonusActor(GameVersusScreen screen, int origX, int origY) {
		this.screen = screen;
		this.origX = origX;
		this.origY = origY;
		// A commenter pour le porting gwt
		font = PuyoPuyo.getInstance().manager.get("images/font_score.fnt", BitmapFont.class);
		font.setColor(1f, 1f, 1f, 1f);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		String score = I18nManager.getInstance().getString("bonus") + String.valueOf(screen.getTimeBonus());
		font.setColor(0f, 0f, 0f, 1f);
		font.setScale(0.7f);
		font.draw(batch, score, origX, origY);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
