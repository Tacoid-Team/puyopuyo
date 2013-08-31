package com.tacoid.pweek;

import com.google.android.gms.games.multiplayer.realtime.Room;
import com.tacoid.pweek.ScoreManager.GameType;

public interface IGameService {

	public enum LeaderboardType {
		SOLO,
		CHRONO,
		ALL
	}
	
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
		P10K(false);
		
		private Achievement(boolean i) {
			incremental=i;
		}
		
		public boolean incremental;
	};
		
	public void login();
	public void logout();

	//get if client is signed in to Google+
	public boolean getSignedIn();

	//submit a score to a leaderboard
	public void submitScore(GameType type, int score);

	//gets the scores and displays them threw googles default widget
	public void showLeaderboard(LeaderboardType type);

	public void showAchievements();
	
	public void showFriendSelector();
	
	public void unlockAchievement(Achievement a);
	
	public void startQuickGame();
	
	public void showWaitingRoom(Room r);
	
	//gets the score and gives access to the raw score data
	public void getScoresData();
}
