package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.pweek.logic.Coord;
import com.tacoid.pweek.logic.Falling;
import com.tacoid.pweek.logic.GameLogic;
import com.tacoid.pweek.logic.State;

public class GridActor extends Actor {

	private TextureRegion[] boules = new TextureRegion[6];
	private TextureRegion[] boules_f = new TextureRegion[6];
	private TextureRegion[] boules_fall = new TextureRegion[6];
	private TextureRegion[] boules_h = new TextureRegion[6];
	private TextureRegion[] boules_v = new TextureRegion[6];
	private TextureRegion next_nuisance_big;
	private TextureRegion next_nuisance_small;
	private GameLogic logic;
	private int origX;
	private int origY;
	private TextureRegion white;
	private int size;
	private int offWhite;
	//private BitmapFont font;

	/**
	 * 
	 * @param logic
	 * @param origX
	 * @param origY
	 * @param size Taille d'une colonne.
	 * @param sizePuyo Taille d'un puyo
	 */
	public GridActor(TextureAtlas atlasPuyo, GameLogic logic, BitmapFont font, int origX, int origY, int size, int sizePuyo) {
		this.origX = origX;
		this.origY = origY;
		this.logic = logic;
		this.size = size;
		//this.font = font;
		this.offWhite = (size - sizePuyo) / 2;
		
		boules[0] = atlasPuyo.findRegion("green_happy-" + sizePuyo);
		boules[1] = atlasPuyo.findRegion("yellow_happy-" + sizePuyo);
		boules[2] = atlasPuyo.findRegion("red_happy-" + sizePuyo);
		boules[3] = atlasPuyo.findRegion("blue_happy-" + sizePuyo);
		boules[4] = atlasPuyo.findRegion("ninja_happy-" + sizePuyo);
		boules[5] = atlasPuyo.findRegion("nuisance-" + sizePuyo);
		
		boules_f[0] = atlasPuyo.findRegion("green_sleep-" + sizePuyo);
		boules_f[1] = atlasPuyo.findRegion("yellow_sleep-" + sizePuyo);
		boules_f[2] = atlasPuyo.findRegion("red_sleep-" + sizePuyo);
		boules_f[3] = atlasPuyo.findRegion("blue_sleep-" + sizePuyo);
		boules_f[4] = atlasPuyo.findRegion("ninja_happy-" + sizePuyo);
		boules_f[5] = atlasPuyo.findRegion("nuisance-" + sizePuyo);
		
		boules_fall[0] = atlasPuyo.findRegion("green_fall-" + sizePuyo);
		boules_fall[1] = atlasPuyo.findRegion("yellow_happy-" + sizePuyo);
		boules_fall[2] = atlasPuyo.findRegion("red_fall-" + sizePuyo);
		boules_fall[3] = atlasPuyo.findRegion("blue_fall-" + sizePuyo);
		boules_fall[4] = atlasPuyo.findRegion("ninja_fall-" + sizePuyo);
		boules_fall[5] = atlasPuyo.findRegion("nuisance_fall-" + sizePuyo);
		
		boules_h[0] = atlasPuyo.findRegion("green_horizontal-" + sizePuyo);
		boules_h[1] = atlasPuyo.findRegion("yellow_horizontal-" + sizePuyo);
		boules_h[2] = atlasPuyo.findRegion("red_horizontal-" + sizePuyo);
		boules_h[3] = atlasPuyo.findRegion("blue_horizontal-" + sizePuyo);
		boules_h[4] = atlasPuyo.findRegion("black_horizontal-" + sizePuyo);

		boules_v[0] = atlasPuyo.findRegion("green_vertical-" + sizePuyo);
		boules_v[1] = atlasPuyo.findRegion("yellow_vertical-" + sizePuyo);
		boules_v[2] = atlasPuyo.findRegion("red_vertical-" + sizePuyo);
		boules_v[3] = atlasPuyo.findRegion("blue_vertical-" + sizePuyo);
		boules_v[4] = atlasPuyo.findRegion("black_vertical-" + sizePuyo);
		
		next_nuisance_big = atlasPuyo.findRegion("next_nuisance-" + ((sizePuyo * 3) / 4));
		next_nuisance_small = atlasPuyo.findRegion("next_nuisance-" + (sizePuyo / 2));
		
		white = atlasPuyo.findRegion("white-" + sizePuyo);
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		int garbage = logic.garbage;
		int offset = 0;
		
		while (garbage >= GameLogic.COLUMNS) {
			batch.draw(next_nuisance_big, origX + offset, origY + 5 + GameLogic.LINES * size - size / 4);
			offset += size * 3 / 4;
			garbage -= GameLogic.COLUMNS;
		}
		while (garbage > 0) {
			batch.draw(next_nuisance_small, origX + offset, origY + 5 + GameLogic.LINES * size - size / 4);
			offset += size / 2;
			garbage--;
		}
		
		int[][] grid = logic.getGrid();
		
		for (int l = 0; l < GameLogic.LINES; l++) {
			for (int c = 0; c < GameLogic.COLUMNS; c++) {
				if (grid[l][c] > 0 && grid[l][c] != logic.GARBAGE) {
					if (c < GameLogic.COLUMNS - 1 && grid[l][c] == grid[l][c+1]) {
						batch.draw(boules_h[grid[l][c] - 1], c * size + origX + size / 2, l * size + origY);
					}
					if (l < GameLogic.LINES - 1 && grid[l][c] == grid[l+1][c]) {
						batch.draw(boules_v[grid[l][c] - 1], c * size + origX, l * size + origY + size / 2);
					}
				}
				
			}
		}
		
		for (int l = 0; l < GameLogic.LINES; l++) {
			for (int c = 0; c < GameLogic.COLUMNS; c++) {
				if (grid[l][c] > 0) {
					batch.draw(boules_f[grid[l][c] - 1], c * (size + 1) + origX,
							l * size + origY);
				}
			}
		}

		if (logic.getState() != State.LOST) {
			Coord[] nextC = logic.getPiece().interpolatedCoord(); 
			if (nextC[0].coul > 0 && nextC[0].l < GameLogic.LINES) {
				batch.draw(white, nextC[0].c * (size + 1) + origX - offWhite, 
						nextC[0].l * size + origY - offWhite);
				batch.draw(boules[nextC[0].coul - 1],
						nextC[0].c * (size + 1) + origX, nextC[0].l * size + origY);
			}
			if (nextC[1].coul > 0 && nextC[1].l < GameLogic.LINES) {
				batch.draw(boules[nextC[1].coul - 1],
						nextC[1].c * (size + 1) + origX, nextC[1].l * size + origY);
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
	public Actor hit(float x, float y, boolean touchable) {
		// TODO Auto-generated method stub
		return null;
	}

}
