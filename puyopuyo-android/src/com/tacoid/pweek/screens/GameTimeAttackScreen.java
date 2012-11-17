package com.tacoid.pweek.screens;

import com.tacoid.pweek.ScoreManager.GameType;
import com.tacoid.pweek.actors.TimeActor;
import com.tacoid.pweek.logic.State;

public class GameTimeAttackScreen extends GameScreenPortrait {
	
	private static GameTimeAttackScreen instance = null;

	public void initGraphics() {
		super.initGraphics();
		TimeActor timeActor = new TimeActor(this, 140, 835);
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
	
	@Override
	public GameType getGameType() {
		return GameType.CHRONO;
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
