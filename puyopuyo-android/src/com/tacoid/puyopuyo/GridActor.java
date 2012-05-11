package com.tacoid.puyopuyo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.logic.Coord;
import com.tacoid.puyopuyo.logic.GameLogic;

public class GridActor extends Actor {

	private int origX = 124;
	private int origY = 16;
	private Texture[] boules = new Texture[4];
	private PuyoPuyo puyopuyo;
	private GameLogic logic;
	
	public GridActor(GameLogic logic) {
		this.logic = logic;
		puyopuyo = PuyoPuyo.getInstance();
		boules[0] = puyopuyo.manager.get("images/vert.png", Texture.class);
		boules[1] = puyopuyo.manager.get("images/jaune.png", Texture.class);
		boules[2] = puyopuyo.manager.get("images/rouge.png", Texture.class);
		boules[3] = puyopuyo.manager.get("images/bleu.png", Texture.class);
	}
	
	@Override
	public void draw(SpriteBatch batch, float alpha) {
		int[][] grid = logic.getGrid();
		for (int l = 0; l < logic.LINES; l++) {
			for (int c = 0; c < logic.COLUMNS; c++) {
				if (grid[l][c] > 0) {
					batch.draw(boules[grid[l][c] - 1], c * 32 + origX, l * 32 + origY);
				}
			}
		}
		for (Coord c : logic.getPiece()) {
			if (c.coul > 0) {
				batch.draw(boules[c.coul - 1], c.c * 32 + origX, c.l * 32 + origY);
			}
		}
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
