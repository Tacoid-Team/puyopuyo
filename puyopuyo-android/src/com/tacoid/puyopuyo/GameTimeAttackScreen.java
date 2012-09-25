package com.tacoid.puyopuyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.tacoid.puyopuyo.actors.TimeActor;
import com.tacoid.puyopuyo.logic.State;

public class GameTimeAttackScreen extends GameScreenPortrait {
	
	private static GameTimeAttackScreen instance = null;
	private static boolean initialized = false;
	
	protected void initGraphics() {
		super.initGraphics();
		TimeActor timeActor = new TimeActor(this, 30, 830);
		stage.addActor(timeActor);
	}

	public static GameTimeAttackScreen getInstance() {
		if (instance == null) {
			instance = new GameTimeAttackScreen();
		}
		if (!initialized) {
			instance.initGraphics();
			initialized = true;
		}
		return instance;
	}

	protected boolean gameEnded() {
		return gameLogic.getState() == State.LOST || elapsedTime >= 120;
	}
	
	public float getTimeLeft() {
		return 120 - elapsedTime;
	}
}
