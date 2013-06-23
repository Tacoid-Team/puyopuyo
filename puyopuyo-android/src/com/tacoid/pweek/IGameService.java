package com.tacoid.pweek;

import com.tacoid.pweek.ScoreManager.GameType;

public interface IGameService {
	public enum Achievement {
		NINJA(false),
		FOREVER_ALONE(false),
		OCD(false),
		CHAIN(false),
		MEGA_EXPLODE(false),
		MASTERSTROKE(false),
		FIRST_COMBO(false),
		AFK(false),
		DEAF(false),
		FANBOY(true),
		P10K(false),
		CURIOUS(false);
		
		private Achievement(boolean i) {
			incremental=i;
		}
		
		boolean incremental;
	};
	
	public void login();
	public void logout();

	//get if client is signed in to Google+
	public boolean getSignedIn();

	//submit a score to a leaderboard
	public void submitScore(GameType type, int score);

	//gets the scores and displays them threw googles default widget
	public void showAllLeaderboards();

	public void showAchievements();
	
	public void unlockAchievement(Achievement a);
	
	//gets the score and gives access to the raw score data
	public void getScoresData();
}
