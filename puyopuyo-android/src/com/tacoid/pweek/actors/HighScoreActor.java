package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.pweek.I18nManager;
import com.tacoid.pweek.ScoreManager;
import com.tacoid.pweek.screens.GameScreen;

public class HighScoreActor extends Actor {
	private BitmapFont font;
	private int origX;
	private int origY;
	private GameScreen screen;
	

	public HighScoreActor(BitmapFont font, GameScreen screen, int origX, int origY) {
		this.screen = screen;
		this.origX = origX;
		this.origY = origY;
		this.font = font;
		font.setColor(0, 0, 0, 1.0f);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		String highScore = I18nManager.getInstance().getString("record") + String.valueOf(ScoreManager.getInstance().getScore(screen.getGameType()));
		font.setColor(0, 0, 0, 1.0f);
		font.setScale(1.0f);
		font.draw(batch, highScore, origX - font.getBounds(highScore).width/2, origY);
	}

	@Override
	public Actor hit(float arg0, float arg1, boolean touchable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
