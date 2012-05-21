package com.tacoid.puyopuyo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.logic.Coord;
import com.tacoid.puyopuyo.logic.Falling;
import com.tacoid.puyopuyo.logic.GameLogic;
import com.tacoid.puyopuyo.logic.State;

public class GridActor extends Actor {

	private Texture[] boules = new Texture[4];
	private Texture[] boules_f = new Texture[4];
	private PuyoPuyo puyopuyo;
	private GameLogic logic;
	private int origX;
	private int origY;
	private Texture white;

	public GridActor(GameLogic logic, int origX, int origY) {
		this.origX = origX;
		this.origY = origY;
		this.logic = logic;
		puyopuyo = PuyoPuyo.getInstance();
		boules[0] = puyopuyo.manager.get("images/vert.png", Texture.class);
		boules[1] = puyopuyo.manager.get("images/jaune.png", Texture.class);
		boules[2] = puyopuyo.manager.get("images/rouge.png", Texture.class);
		boules[3] = puyopuyo.manager.get("images/bleu.png", Texture.class);

		boules_f[0] = puyopuyo.manager.get("images/vert_f.png", Texture.class);
		boules_f[1] = puyopuyo.manager.get("images/jaune_f.png", Texture.class);
		boules_f[2] = puyopuyo.manager.get("images/rouge_f.png", Texture.class);
		boules_f[3] = puyopuyo.manager.get("images/bleu_f.png", Texture.class);

		white = puyopuyo.manager.get("images/white.png", Texture.class);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		int[][] grid = logic.getGrid();
		for (int l = 0; l < logic.LINES; l++) {
			for (int c = 0; c < logic.COLUMNS; c++) {
				if (grid[l][c] > 0) {
					batch.draw(boules_f[grid[l][c] - 1], c * 32 + origX, l * 32
							+ origY);
				}
			}
		}

		if (logic.getState() != State.LOST) {
			Coord[] next = logic.getPiece();
			if (next != null) {
				if (next[0].coul > 0) {
					batch.draw(white, next[0].c * 32 + origX, next[0].l * 32
							+ origY);
					batch.draw(boules[next[0].coul - 1],
							next[0].c * 32 + origX, next[0].l * 32 + origY);
				}
				if (next[1].coul > 0) {
					batch.draw(boules[next[1].coul - 1],
							next[1].c * 32 + origX, next[1].l * 32 + origY);
				}
			}
		}

		if (logic.getState() == State.GRAVITY) {
			if (logic.getFallings() != null) {
				for (Falling f : logic.getFallings()) {
					Coord end = f.getEnd();
					batch.draw(boules[end.coul - 1], end.c * 32 + origX, end.l
							* 32 + f.getRemaining() * 32 + origY);
				}
			}
		}
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
