package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.GameScreen;
import com.tacoid.puyopuyo.I18nManager;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.ScoreManager;

public class HighScoreActor extends Actor {
	private BitmapFont font;
	private int origX;
	private int origY;
	private GameScreen screen;
	

	public HighScoreActor(GameScreen screen, int origX, int origY) {
		this.screen = screen;
		this.origX = origX;
		this.origY = origY;
		// A commenter pour le porting gwt
		font = PuyoPuyo.getInstance().manager.get("images/font_score.fnt", BitmapFont.class);
		font.setColor(0, 0, 0, 1.0f);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		String highScore = I18nManager.getInstance().getString("record") + String.valueOf(ScoreManager.getInstance().getScore(screen.getGameType()));
		font.setColor(0, 0, 0, 1.0f);
		font.setScale(1.0f);
		font.draw(batch, highScore, origX - font.getBounds(highScore).width, origY);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
