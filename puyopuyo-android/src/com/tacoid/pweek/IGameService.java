package com.tacoid.pweek;

import com.tacoid.pweek.ScoreManager.GameType;

public interface IGameService {
	public enum Achievement {
		AFK(false);
		
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
	public void showLeaderboard();

	public void showAchievements();
	
	public void unlockAchievement(Achievement a);
	
	//gets the score and gives access to the raw score data
	public void getScoresData();
}
