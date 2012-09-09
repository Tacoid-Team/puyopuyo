package com.tacoid.puyopuyo;

import com.tacoid.puyopuyo.actors.TimeActor;
import com.tacoid.puyopuyo.logic.State;

public class GameTimeAttackScreen extends GameSoloScreen {
	
	private static GameTimeAttackScreen instance = null;
	
	private GameTimeAttackScreen() {
		super();
		TimeActor timeActor = new TimeActor(this, 50, 800);
		stage.addActor(timeActor);
	}

	public static GameTimeAttackScreen getInstance() {
		if (instance == null) {
			instance = new GameTimeAttackScreen();
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
