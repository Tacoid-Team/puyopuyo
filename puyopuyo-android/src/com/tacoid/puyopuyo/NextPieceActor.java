package com.tacoid.puyopuyo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.logic.Coord;
import com.tacoid.puyopuyo.logic.GameLogic;

public class NextPieceActor extends Actor {

	private int origX;
	private int origY;
	private GameLogic logic;
	private Texture[] boules = new Texture[4];

	public NextPieceActor(GameLogic logic, int origX, int origY) {
		this.logic = logic;
		this.origX = origX;
		this.origY = origY;
		PuyoPuyo puyopuyo = PuyoPuyo.getInstance();
		boules[0] = puyopuyo.manager.get("images/vert.png", Texture.class);
		boules[1] = puyopuyo.manager.get("images/jaune.png", Texture.class);
		boules[2] = puyopuyo.manager.get("images/rouge.png", Texture.class);
		boules[3] = puyopuyo.manager.get("images/bleu.png", Texture.class);
	}
	
	@Override
	public void draw(SpriteBatch batch, float alpha) {
		Coord[] piece = logic.getNextPiece();
		if (piece != null) {
			if (piece[0].coul > 0)
				batch.draw(boules[piece[0].coul-1], origX + (piece[0].c - logic.COLUMNS / 2) * 32, origY + (piece[0].l - logic.LINES + 1) * 32);
			if (piece[1].coul > 0)
				batch.draw(boules[piece[1].coul-1], origX + (piece[1].c - logic.COLUMNS / 2) * 32, origY + (piece[1].l - logic.LINES + 1) * 32);
		}
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
