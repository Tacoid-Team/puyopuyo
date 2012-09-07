package com.tacoid.puyopuyo.actors;

import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.logic.GameLogic;
import com.tacoid.puyopuyo.logic.GameLogic.MoveType;

public class MoveButton extends Button{

	private long last = 0;
	private GameLogic gameLogic;
	private MoveType direction;

	public MoveButton(MoveType dir, GameLogic logic, float x, float y, TextureRegion regionUp, TextureRegion regionDown) {
		super(regionUp, regionDown);
		direction = dir;
		gameLogic = logic;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer) {
		//if (end) return false;
		super.touchDown(x, y, pointer);
		switch(direction) {
		case DOWN:
			gameLogic.down();
			break;
		case LEFT:
			gameLogic.moveLeft();
			break;
		case RIGHT:
			gameLogic.moveRight();
			break;
		case ROT_RIGHT:
			gameLogic.rotateRight();
			break;
		case ROT_LEFT:
			gameLogic.rotateLeft();
			break;
		default:
			break;
		}
		//gameLogic.dropPiece();
		return true;
	}

	@Override
	public void touchUp(float x, float y, int pointer) {
		//if (end) return;
		super.touchUp(x, y, pointer);
		if(direction == MoveType.DOWN) {
			gameLogic.up();			
			if (Calendar.getInstance().getTimeInMillis() - last < 500) {
				gameLogic.dropPiece();
			}
			
			last  = Calendar.getInstance().getTimeInMillis();
		}
	}
}
