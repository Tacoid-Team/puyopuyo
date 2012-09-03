package com.tacoid.puyopuyo;

import com.tacoid.puyopuyo.logic.State;

public class GameTimeAttackScreen extends GameSoloScreen {
	
	private static GameTimeAttackScreen instance = null;
	
	private GameTimeAttackScreen() {
		super();
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
	
}
