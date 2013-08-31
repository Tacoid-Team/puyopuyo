package com.tacoid.pweek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.google.android.gms.games.GamesClient;
import com.tacoid.pweek.IGameService.Achievement;
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
	
	public void setScore(IGameService gameService, GameType type, int score) {
		int highScore = getScore(type);
	
		if(highScore < score && type != GameType.VERSUS_IA) {
			pref.putInteger(type.toString(), score);
			TrackingManager.getTracker().trackEvent("gameplay", "new_score", type.toString(), (long) score);
			pref.flush();
		}
		
		gameService.submitScore(type,score);
		
		if (type == GameType.SOLO && score >= 10000) {
			Pweek.getInstance().getGameService().unlockAchievement(Achievement.P10K);
		}
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
