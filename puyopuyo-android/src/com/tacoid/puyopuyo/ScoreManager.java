package com.tacoid.puyopuyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreManager {
	public enum GameType {
		SOLO,
		VERSUS_IA,
		CHRONO
	};
	/* Static part */
	static ScoreManager instance = null;

	static public ScoreManager getInstance() {
		if(instance == null) {
			instance = new ScoreManager();
		}
		return instance;
	}
	/* ********** */
	
	
	Preferences pref;
	
	private ScoreManager() {
		pref = Gdx.app.getPreferences("game_scores");
	}
	
	public int getScore(GameType type) {
		return pref.getInteger(type.toString(), 0);
	}
	
	public void setScore(GameType type, int score) {
		pref.putInteger(type.toString(), score);
		pref.flush();
	}

}
