package com.tacoid.pweek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.android.gms.games.GamesClient;
import com.tacoid.tracking.TrackingManager;

public class ScoreManager {
	public enum GameType {
		SOLO,
		VERSUS_IA,
		CHRONO
	};
	
	/* Static part */
	static ScoreManager instance = null;
	
	GamesClient mGamesClient;

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
		Pweek.getInstance().getGameService().submitScore(type,score);
		
		TrackingManager.getTracker().trackEvent("gameplay", "new_score", type.toString(), (long) score);
		pref.flush();
	}
	
	public boolean isLevelUnlocked(GameType type, int level) {
		if(level == 0)
			return true;
		else 
			return pref.getBoolean(type.toString()+"_"+level+"_unlocked", false);
	}
	
	public void unlockLevel(GameType type, int level) {
		pref.putBoolean(type.toString()+"_"+level+"_unlocked", true);
		pref.flush();
	}

}
