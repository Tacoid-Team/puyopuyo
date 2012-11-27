package com.tacoid.pweek;

import java.util.Calendar;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tacoid.pweek.Pweek.ScreenOrientation;
import com.tacoid.pweek.logic.GameLogic;
import com.tacoid.pweek.screens.GameScreen;

public class Controller implements InputProcessor {

	private GameLogic gameLogic;
	private Stage stage;
	private GameScreen gameScreen;
	private int downX;
	private int downY;
	private long last = 0;

	public Controller(GameLogic gameLogic, GameScreen gameScreen, Stage stage) {
		this.gameLogic = gameLogic;
		this.gameScreen = gameScreen;
		this.stage = stage;
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
			gameScreen.quit();
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
		if (gameScreen.getOrientation() == ScreenOrientation.PORTRAIT) {
			this.downX = -y;
			this.downY = x;
		} else {
			this.downX = x;
			this.downY = y;
		}
		return stage.touchDown(x, y, pointer, button);
	}

	@Override
	public boolean touchDragged(int x, int y, int arg2) {
		if (gameScreen.getOrientation() == ScreenOrientation.PORTRAIT) {
			int buf = x;
			x = -y;
			y = buf;
		}
		
		if (x - downX > gameScreen.getPuyoSize()) {
			if (gameLogic.moveRight()) {
				downX = x;
				downY = y;
			}
			last = 0;
		} else if (x - downX < -gameScreen.getPuyoSize()) {
			if (gameLogic.moveLeft()) {
				downX = x;
				downY = y;
			}
			last = 0;
		} else if (y - downY > gameScreen.getPuyoSize() * 3) {
			gameLogic.dropPiece();
			downY = y;
			last = 0;
		}
		
		return stage.touchDragged(x, y, arg2);
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return stage.touchMoved(x, y);
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (stage.touchUp(x, y, pointer, button)) {
			return true;
		} else { 
			if (Calendar.getInstance().getTimeInMillis() - last < 500) {
				gameLogic.rotateLeft();
			}
			last = Calendar.getInstance().getTimeInMillis();
		}
		return false;
	}

}
