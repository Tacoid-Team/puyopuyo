package com.tacoid.pweek.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.pweek.I18nManager;
import com.tacoid.pweek.screens.GameScreen;

public class LevelActor extends Actor {
	private GameScreen screen;
	private BitmapFont font;
	private int origX;
	private int origY;
	

	public LevelActor(AssetManager manager, GameScreen screen, int origX, int origY) {
		this.screen = screen;
		this.origX = origX;
		this.origY = origY;
		
		font = manager.get("images/font_level.fnt", BitmapFont.class);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		String level = I18nManager.getInstance().getString("niveau")  + String.valueOf(screen.getLevel());
		font.setColor(1f, 1f, 1f, 1f);
		font.setScale(1.0f);
		font.draw(batch, level, origX - font.getBounds(level).width / 2, origY);
	}

	@Override
	public Actor hit(float arg0, float arg1, boolean touchable) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
