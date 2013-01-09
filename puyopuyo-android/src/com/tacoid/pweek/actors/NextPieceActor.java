package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.pweek.logic.Coord;
import com.tacoid.pweek.logic.GameLogic;

public class NextPieceActor extends Actor {

	private int size;
	private int origX;
	private int origY;
	private GameLogic logic;
	private TextureRegion[] boules = new TextureRegion[5];

	public NextPieceActor(TextureAtlas atlasPuyo, GameLogic logic, int origX, int origY, int size) {
		this.logic = logic;
		this.origX = origX;
		this.origY = origY;
		this.size = size + 2;
		boules[0] = atlasPuyo.findRegion("green_happy-" + size);
		boules[1] = atlasPuyo.findRegion("yellow_happy-" + size);
		boules[2] = atlasPuyo.findRegion("red_happy-" + size);
		boules[3] = atlasPuyo.findRegion("blue_happy-" + size);
		boules[4] = atlasPuyo.findRegion("ninja_happy-" + size);
	}
	
	@Override
	public void draw(SpriteBatch batch, float alpha) {
		Coord[] piece = logic.getNextPiece().coords;
		if (piece != null) {
			if (piece[0].coul > 0)
				batch.draw(boules[piece[0].coul-1], origX + (piece[0].c - GameLogic.COLUMNS / 2) * size, origY + (piece[0].l - GameLogic.LINES + 1) * size);
			if (piece[1].coul > 0)
				batch.draw(boules[piece[1].coul-1], origX + (piece[1].c - GameLogic.COLUMNS / 2) * size, origY + (piece[1].l - GameLogic.LINES + 1) * size);
		}
	}

	@Override
	public Actor hit(float arg0, float arg1, boolean touchable) {
		// TODO Auto-generated method stub
		return null;
	}

}
