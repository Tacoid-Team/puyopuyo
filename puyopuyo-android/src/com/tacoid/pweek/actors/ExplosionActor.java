package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.pweek.logic.Coord;
import com.tacoid.pweek.logic.Explosion;
import com.tacoid.pweek.logic.GameLogic;

public class ExplosionActor extends Actor {

	private TextureRegion[] boules_fall = new TextureRegion[6];
	private GameLogic logic;
	private int origX;
	private int origY;
	private int size;
	private BitmapFont font;

	public ExplosionActor(TextureAtlas atlasPuyo, GameLogic logic, BitmapFont font, int origX, int origY, int size, int sizePuyo) {
		this.origX = origX;
		this.origY = origY;
		this.logic = logic;
		this.size = size;
		this.font = font;
		
		boules_fall[0] = atlasPuyo.findRegion("green_fall-" + sizePuyo);
		boules_fall[1] = atlasPuyo.findRegion("yellow_happy-" + sizePuyo);
		boules_fall[2] = atlasPuyo.findRegion("red_fall-" + sizePuyo);
		boules_fall[3] = atlasPuyo.findRegion("blue_fall-" + sizePuyo);
		boules_fall[4] = atlasPuyo.findRegion("ninja_fall-" + sizePuyo);
		boules_fall[5] = atlasPuyo.findRegion("nuisance_fall-" + sizePuyo);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		long date = System.currentTimeMillis();
		for (Explosion e : logic.getExplosions()) {
			float barryX = 0.0f;
			float barryY = 0.0f;
			for (Coord c : e.getExplosions()) {
				float v = 0.5f;
				float x, y;
				float t = (date - e.getExplosionDate());
				x = (c.c * (size + 1) + origX) + v * (float)Math.cos(c.angle) * t;
				y = c.l * size + origY - 0.001f * t*t + v * (float)Math.sin(c.angle) * t;
				barryX += x;
				barryY += y;
				batch.draw(boules_fall[c.coul - 1], x, y, size / 2.0f, size / 2.0f, (float)size, (float)size, 1f, 1f, (float)(0.2 * t * (e.angle - 1.5))); 
			}
			barryX = barryX / e.getExplosions().size();
			barryY = barryY / e.getExplosions().size();
			String score = String.valueOf("+"+e.points+"!");
			
			font.setScale(Math.min(4.0f, 
					               Math.max(1.0f, 
					            		   ((float)e.points/200.0f)
					            		   )
					               )
					      );
			
			font.setColor(0.0f, 0.0f, 0.0f, 0.8f);
			font.draw(batch, score, barryX+2, barryY+102);
			font.setColor(1.0f, 1.0f, 1.0f, 1f);
			font.draw(batch, score, barryX, barryY+100);

		}
	}
}

