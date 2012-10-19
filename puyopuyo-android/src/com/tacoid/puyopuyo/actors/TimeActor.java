package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.GameTimeAttackScreen;
import com.tacoid.puyopuyo.PuyoPuyo;

public class TimeActor extends Actor {

	private BitmapFont font;
	private int origX, origY;
	private GameTimeAttackScreen screen;

	public TimeActor(GameTimeAttackScreen screen, int origX, int origY) {
		this.screen = screen;
		this.origX = origX;
		this.origY = origY;
		// A commenter pour le porting gwt
		
		
		font = PuyoPuyo.getInstance().manager.get("images/font_score.fnt", BitmapFont.class);
		font.setScale(0.65f);
		font.setColor(1f, 1f, 1f, 1f);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		String time = "Temps restant : " + String.valueOf((int)screen.getTimeLeft());
		font.draw(batch, time, origX, origY);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
