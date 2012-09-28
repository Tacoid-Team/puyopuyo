package com.tacoid.puyopuyo;

import com.tacoid.puyopuyo.ScoreManager.GameType;
import com.tacoid.puyopuyo.actors.TimeActor;
import com.tacoid.puyopuyo.logic.State;

public class GameTimeAttackScreen extends GameScreenPortrait {
	
	private static GameTimeAttackScreen instance = null;
	private static boolean initialized = false;
	
	protected void initGraphics() {
		super.initGraphics();
		TimeActor timeActor = new TimeActor(this, 30, 830);
		stage.addActor(timeActor);
		initialized = true;
	}

	public static GameTimeAttackScreen getInstance() {
		if (instance == null) {
			instance = new GameTimeAttackScreen();
		}
		if (!initialized) {
			instance.initGraphics();
		}
		return instance;
	}

	protected boolean gameEnded() {
		return gameLogic.getState() == State.LOST || elapsedTime >= 120;
	}
	
	public float getTimeLeft() {
		return 120 - elapsedTime;
	}
	
	@Override
	public GameType getGameType() {
		return GameType.CHRONO;
	}
}
