package com.tacoid.puyopuyo;

import com.tacoid.puyopuyo.actors.LevelActor;

public class GameSoloScreen extends GameScreenPortrait {
	
	private static GameSoloScreen instance;
	private int level;
	private float timeLevel;
	private float speed;

	private GameSoloScreen() {
		super();
		LevelActor levelActor = new LevelActor(this, 80, 830);
		stage.addActor(levelActor);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		if (timeLevel > 30) {
			if (level < 5) {
				level++;
			}
			
			switch (level) {
			case 1:
				speed = 0.5f;
				break;
			case 2:
				speed = 0.4f;
				break;
			case 3:
				speed = 0.3f;
				break;
			case 4:
				speed = 0.2f;
				break;
			default:
				speed = 0.1f;
			}
			
			gameLogic.setSpeed(speed);
			
			timeLevel = 0;
		} else {
			timeLevel += delta;
		}
	}
	
	@Override
	public void init() {
		super.init();
		level = 1;
		timeLevel = 0;
		speed = 0.5f;
		gameLogic.setSpeed(speed);
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
