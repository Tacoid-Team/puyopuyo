package com.tacoid.puyopuyo;

import com.tacoid.puyopuyo.actors.LevelActor;

public class GameSoloScreen extends GameScreenPortrait {
	
	private static GameSoloScreen instance;
	private int level;

	private GameSoloScreen() {
		super();
		LevelActor levelActor = new LevelActor(this, 80, 830);
		stage.addActor(levelActor);
	}

	@Override
	public void init() {
		super.init();
		level = 1;
	}
	
	public static GameSoloScreen getInstance() {
		if (instance == null) {
			instance = new GameSoloScreen();
		}
		return instance;
	}

	public int getLevel() {
		return level;
	}
	
}
