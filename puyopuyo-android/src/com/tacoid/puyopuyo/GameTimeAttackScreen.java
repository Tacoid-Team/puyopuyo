package com.tacoid.puyopuyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.tacoid.puyopuyo.logic.State;

public class GameTimeAttackScreen extends GameSoloScreen {
	
	private static float time_left = 120;
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

	@Override
	public void render(float delta) {
		GLCommon gl = Gdx.gl;

		if (gameLogic.getState() != State.LOST && time_left > 0) {
			// Update model
			gameLogic.update(delta);
			time_left -= delta;
		} else {
			Gdx.input.setInputProcessor(null);
			end = true;
			// TODO
		}

		// clear previous frame
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

}
