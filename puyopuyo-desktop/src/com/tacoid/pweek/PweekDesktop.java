package com.tacoid.pweek;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.tacoid.pweek.IActivityRequestHandler;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.ScoreManager.GameType;
import com.tacoid.tracking.TrackingManager;
import com.tacoid.tracking.TrackingManager.TrackerType;

public class PweekDesktop implements IActivityRequestHandler, IGameService {
	private static PweekDesktop application;
	public static void main(String[] argv) {
		if (application == null) {
			application = new PweekDesktop();
		}
		Pweek.getInstance().setDesktopMode(true);
		Pweek.setGameService(application);
		TrackingManager.setTrackerType(TrackerType.DUMMY);
		Pweek.setHandler(application);
		new LwjglApplication(Pweek.getInstance(), "Pweek",  1280, 768, false);
	}

	private boolean signedIn;

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPortrait(boolean isPortrait) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void login() {
		System.out.println("GameService : Login");
		signedIn = true;
	}

	@Override
	public void logout() {
		System.out.println("GameService : Logout");
		signedIn = false;
	}

	@Override
	public boolean getSignedIn() {
		return signedIn;
	}

	@Override
	public void showLeaderboard(LeaderboardType type) {
		System.out.println("GameService : showLeaderboard");
		
	}

	@Override
	public void getScoresData() {
		System.out.println("GameService : getScoresData");
	}

	@Override
	public void submitScore(GameType type, int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAchievements() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockAchievement(Achievement a) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void showFriendSelector() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startQuickGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showWaitingRoom(Room r) {
		// TODO Auto-generated method stub
		
	}
}