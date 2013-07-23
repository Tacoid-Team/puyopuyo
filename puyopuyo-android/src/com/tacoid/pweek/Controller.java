package com.tacoid.pweek;

import java.util.Calendar;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tacoid.pweek.logic.GameLogic;
import com.tacoid.pweek.screens.GameScreen;

public class Controller implements InputProcessor {

	private GameLogic gameLogic;
	private Stage stage;
	private GameScreen gameScreen;
	private float downX;
	private float downY;
	private long last = 0;
	private Vector2 topleft;
	private Vector2 buttomright;

	public Controller(GameLogic gameLogic, GameScreen gameScreen, Stage stage, Vector2 topleft, Vector2 buttomright) {
		this.gameLogic = gameLogic;
		this.gameScreen = gameScreen;
		this.stage = stage;
		this.topleft = topleft;
		this.buttomright = buttomright;
	}
	
	@Override
	public boolean keyDown(int key) {
		if (key != Keys.BACK && gameLogic.isPaused()) {
			return false;
		}
		switch (key) {
		case Keys.LEFT:
			gameLogic.moveLeft();
			break;
		case Keys.RIGHT:
			gameLogic.moveRight();
			break;
		case Keys.DOWN:
			gameLogic.down();
			break;
		case Keys.ALT_LEFT:
		case Keys.UP:
			gameLogic.rotateRight();
			break;
		case Keys.CONTROL_LEFT:
			gameLogic.rotateLeft();
			break;
		case Keys.SPACE:
			gameLogic.dropPiece();
			break;
		case Keys.BACK:
			if (gameScreen.isGameStarted() == false) {
				gameScreen.quit();
			} else if (gameScreen.isGamePaused()) {
				//gameScreen.quit();
				gameScreen.hidePause(); // C'est selon les gens...
			} else {
				gameScreen.pause();
			}
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int key) {
		switch (key) {
		case Keys.DOWN:
			gameLogic.up();
			break;
		}
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {		
		Vector2 sD = stage.screenToStageCoordinates(new Vector2(x, y));
		if (inArea(sD)) {
			this.downX = sD.x;
			this.downY = sD.y;
			last = Calendar.getInstance().getTimeInMillis();
		} else {
			this.downX = -1;
			this.downY = -1;
		}
		return stage.touchDown(x, y, pointer, button);
	}

	@Override
	public boolean touchDragged(int x, int y, int arg2) {
		Vector2 s = stage.screenToStageCoordinates(new Vector2(x, y));
		if (downX >= 0 && downY >= 0) {
			if (s.x - downX > gameScreen.getPuyoSize()) {
				if (gameLogic.moveRight()) {
					downX = s.x;
					downY = s.y;
				}
				last = 0;
			} else if (s.x - downX < -gameScreen.getPuyoSize()) {
				if (gameLogic.moveLeft()) {
					downX = s.x;
					downY = s.y;
				}
				last = 0;
			} else if (downY - s.y > gameScreen.getPuyoSize() * 2) {
				gameLogic.dropPiece();
				downY = s.y;
				last = 0;
			}
		}
		
		return stage.touchDragged(x, y, arg2);
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		Vector2 s = stage.screenToStageCoordinates(new Vector2(x, y));
		
		if (stage.touchUp(x, y, pointer, button)) {
			return true;
		} else {
			if (inArea(s) && Math.abs(this.downX - s.x) < 20 && Math.abs(this.downY - s.y) < 20 &&    
					Calendar.getInstance().getTimeInMillis() - last < 200) {
				gameLogic.rotateLeft();
			}
		}
		return false;
	}
	
	@Override
	public boolean mouseMoved(int x, int y) {
		return stage.mouseMoved(x, y);
	}

	private boolean inArea(Vector2 s) {
		return (s.x >= topleft.x && s.x <= buttomright.x
				&& s.y >= buttomright.y && s.y <= topleft.y);
	}
}
