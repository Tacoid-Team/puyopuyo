package com.tacoid.pweek.actors;

import java.util.Calendar;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.logic.GameLogic;
import com.tacoid.pweek.logic.GameLogic.MoveType;

public class MoveButton extends Button {

	private long last = 0;
	private GameLogic gameLogic;
	private MoveType direction;

	public MoveButton(MoveType dir, GameLogic logic, float x, float y, TextureRegion regionUp, TextureRegion regionDown) {
		super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown));
		direction = dir;
		gameLogic = logic;
		this.setX(x);
		this.setY(y);
		
		this.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//if (end) return false;
				super.touchDown(event, x, y, pointer, button);
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
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
			
				if(direction == MoveType.DOWN) {
					gameLogic.up();			
					if (Calendar.getInstance().getTimeInMillis() - last < 500) {
						gameLogic.dropPiece();
					}
					
					last  = Calendar.getInstance().getTimeInMillis();
				}
			}		
		});
	}
}
