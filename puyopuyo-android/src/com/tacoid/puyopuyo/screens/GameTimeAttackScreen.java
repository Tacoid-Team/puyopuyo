package com.tacoid.puyopuyo.screens;

import com.tacoid.puyopuyo.ScoreManager.GameType;
import com.tacoid.puyopuyo.actors.TimeActor;
import com.tacoid.puyopuyo.logic.State;

public class GameTimeAttackScreen extends GameScreenPortrait {
	
	private static GameTimeAttackScreen instance = null;

	protected void initGraphics() {
		super.initGraphics();
		TimeActor timeActor = new TimeActor(this, 30, 830);
		stage.addActor(timeActor);
	}

	public static GameTimeAttackScreen getInstance() {
		if (instance == null) {
			instance = new GameTimeAttackScreen();
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

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}