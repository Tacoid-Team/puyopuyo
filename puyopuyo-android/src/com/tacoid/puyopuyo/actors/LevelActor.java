package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.GameSoloScreen;

public class LevelActor extends Actor {
	private GameSoloScreen screen;
	private BitmapFont font;
	private int origX;
	private int origY;
	

	public LevelActor(GameSoloScreen screen, int origX, int origY) {
		this.screen = screen;
		this.origX = origX;
		this.origY = origY;
		// A commenter pour le porting gwt
		font = new BitmapFont();
		font.setScale(2f);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		String level = "Level " + String.valueOf(screen.getLevel());
		font.draw(batch, level, origX, origY);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
