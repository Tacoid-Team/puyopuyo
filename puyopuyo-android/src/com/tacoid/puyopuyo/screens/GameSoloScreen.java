package com.tacoid.puyopuyo.screens;

import com.tacoid.puyopuyo.ScoreManager.GameType;
import com.tacoid.puyopuyo.actors.LevelActor;

public class GameSoloScreen extends GameScreenPortrait {

	private static GameSoloScreen instance;
	public static boolean initialized = false;
	private int level;
	private float timeLevel;
	private float speed;
	private int n_colors;
	private float timeGarbage;
	
	protected void initGraphics() {
		super.initGraphics();
		LevelActor levelActor = new LevelActor(this, 140, 830);
		stage.addActor(levelActor);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		if (!gameLogic.isPaused() && !gameEnded()) {
			if (level >= 5 && timeGarbage > 25.0f / level) {
				gameLogic.sendGarbage(1);
				timeGarbage = 0;
			} else {
				timeGarbage += delta;
			}
			if (gameLogic.getScore() >= level * 1000 || timeLevel > 120) {
				if (level < 10) {
					level++;
				}

				switch (level) {
				case 2:
					n_colors = 4;
					break;
				case 3:
					speed = 0.4f;
					break;
				case 4:
					n_colors = 5;
					break;
				case 6:
					speed = 0.3f;
					break;
				case 8:
					speed = 0.25f;
					break;
				case 10:
					speed = 0.2f;
				}

				gameLogic.setNColors(n_colors);
				gameLogic.setSpeed(speed);

				timeLevel = 0;
			} else {
				timeLevel += delta;
			}
		}
	}

	@Override
	public void init() {
		level = 1;
		timeLevel = 0;
		timeGarbage = 0;
		speed = 0.5f;
		n_colors = 3;
		gameLogic.setSpeed(speed);
		gameLogic.setNColors(n_colors);
		super.init();
	}

	public static GameSoloScreen getInstance() {
		if (instance == null) {
			instance = new GameSoloScreen();
			instance.initGraphics();
		}
		return instance;
	}

	public int getLevel() {
		return level;
	}
	
	@Override
	public GameType getGameType() {
		return GameType.SOLO;
	}

}
