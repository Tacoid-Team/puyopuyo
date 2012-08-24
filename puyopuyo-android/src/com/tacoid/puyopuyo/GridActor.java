package com.tacoid.puyopuyo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.logic.Coord;
import com.tacoid.puyopuyo.logic.Falling;
import com.tacoid.puyopuyo.logic.GameLogic;
import com.tacoid.puyopuyo.logic.State;

public class GridActor extends Actor {

	private Texture[] boules = new Texture[5];
	private Texture[] boules_f = new Texture[5];
	private Texture[] boules_fall = new Texture[5];
	private Texture[] boules_h = new Texture[5];
	private Texture[] boules_v = new Texture[5];
	private PuyoPuyo puyopuyo;
	private GameLogic logic;
	private int origX;
	private int origY;
	private Texture white;
	private int size;
	private int sizePuyo;
	private int offWhite;

	/**
	 * 
	 * @param logic
	 * @param origX
	 * @param origY
	 * @param size Taille d'une colonne.
	 * @param sizePuyo Taille d'un puyo
	 */
	public GridActor(GameLogic logic, int origX, int origY, int size, int sizePuyo) {
		this.origX = origX;
		this.origY = origY;
		this.logic = logic;
		this.size = size;
		this.sizePuyo = sizePuyo;
		this.offWhite = (size - sizePuyo) / 2;
		puyopuyo = PuyoPuyo.getInstance();
		
		// TODO: changer nom fichiers pour intégrer taille.
		boules[0] = puyopuyo.manager.get("images/vert.png", Texture.class);
		boules[1] = puyopuyo.manager.get("images/jaune.png", Texture.class);
		boules[2] = puyopuyo.manager.get("images/rouge.png", Texture.class);
		boules[3] = puyopuyo.manager.get("images/bleu.png", Texture.class);
		boules[4] = puyopuyo.manager.get("images/nuisance.png", Texture.class);

		boules_f[0] = puyopuyo.manager.get("images/vert_f.png", Texture.class);
		boules_f[1] = puyopuyo.manager.get("images/jaune_f.png", Texture.class);
		boules_f[2] = puyopuyo.manager.get("images/rouge_f.png", Texture.class);
		boules_f[3] = puyopuyo.manager.get("images/bleu_f.png", Texture.class);
		boules_f[4] = puyopuyo.manager.get("images/nuisance.png", Texture.class);
		
		boules_fall[0] = puyopuyo.manager.get("images/vert.png", Texture.class);
		boules_fall[1] = puyopuyo.manager.get("images/jaune.png", Texture.class);
		boules_fall[2] = puyopuyo.manager.get("images/red_fall_48.png", Texture.class);
		boules_fall[3] = puyopuyo.manager.get("images/bleu.png", Texture.class);
		boules_fall[4] = puyopuyo.manager.get("images/nuisance.png", Texture.class);
		
		
		boules_h[0] = puyopuyo.manager.get("images/vert_horizontal.png", Texture.class);
		boules_h[1] = puyopuyo.manager.get("images/jaune_horizontal.png", Texture.class);
		boules_h[2] = puyopuyo.manager.get("images/rouge_horizontal.png", Texture.class);
		boules_h[3] = puyopuyo.manager.get("images/bleu_horizontal.png", Texture.class);

		boules_v[0] = puyopuyo.manager.get("images/vert_vertical.png", Texture.class);
		boules_v[1] = puyopuyo.manager.get("images/jaune_vertical.png", Texture.class);
		boules_v[2] = puyopuyo.manager.get("images/rouge_vertical.png", Texture.class);
		boules_v[3] = puyopuyo.manager.get("images/bleu_vertical.png", Texture.class);
		
		white = puyopuyo.manager.get("images/white.png", Texture.class);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		int[][] grid = logic.getGrid();
		
		for (int l = 0; l < logic.LINES - 1; l++) {
			for (int c = 0; c < logic.COLUMNS - 1; c++) {
				if (grid[l][c] > 0 && grid[l][c] != logic.GARBAGE) {
					if (grid[l][c] == grid[l][c+1]) {
						batch.draw(boules_h[grid[l][c] - 1], c * size + origX + size / 2, l * size + origY);
					}
					if (grid[l][c] == grid[l+1][c]) {
						batch.draw(boules_v[grid[l][c] - 1], c * size + origX, l * size + origY + size / 2);
					}
				}
				
			}
		}
		
		for (int l = 0; l < logic.LINES; l++) {
			for (int c = 0; c < logic.COLUMNS; c++) {
				if (grid[l][c] > 0) {
					batch.draw(boules_f[grid[l][c] - 1], c * (size + 1) + origX,
							l * size + origY);
				}
			}
		}

		if (logic.getState() != State.LOST) {
			Coord[] next = logic.getPiece();
			if (next != null) {
				if (next[0].coul > 0 && next[0].l < logic.LINES) {
					batch.draw(white, next[0].c * (size + 1) + origX - offWhite, 
							next[0].l * size + origY + offWhite);
					batch.draw(boules[next[0].coul - 1],
							next[0].c * (size + 1) + origX, next[0].l * size + origY);
				}
				if (next[1].coul > 0 && next[1].l < logic.LINES) {
					batch.draw(boules[next[1].coul - 1],
							next[1].c * (size + 1) + origX, next[1].l * size + origY);
				}
			}
		}

		if (logic.getState() == State.GRAVITY) {
			if (logic.getFallings() != null) {
				for (Falling f : logic.getFallings()) {
					Coord end = f.getEnd();
					batch.draw(boules_fall[end.coul - 1], end.c * (size + 1) + origX, end.l
							* size + f.getRemaining() * size + origY);
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
