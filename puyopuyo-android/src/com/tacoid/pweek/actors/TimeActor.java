package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.pweek.I18nManager;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.screens.GameTimeAttackScreen;

public class TimeActor extends Actor {

	private BitmapFont font;
	private int origX, origY;
	private GameTimeAttackScreen screen;
	private String timeText;
	private float offset;

	public TimeActor(GameTimeAttackScreen screen, int origX, int origY) {
		this.screen = screen;
		this.origX = origX;
		this.origY = origY;
		
		font = Pweek.getInstance().manager.get("images/font_score.fnt", BitmapFont.class);
		font.setScale(0.65f);
		font.setColor(1f, 1f, 1f, 1f);
		
		this.timeText = I18nManager.getInstance().getString("temps_restant");
		this.offset = font.getBounds(timeText + "XXX").width / 2;
		
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		String time = timeText + String.valueOf((int)screen.getTimeLeft());
		font.setScale(0.65f);
		font.setColor(1f, 1f, 1f, 1f);
		font.draw(batch, time, origX - offset, origY);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
