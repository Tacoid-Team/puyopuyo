package com.tacoid.pweekmini;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.tacoid.pweek.IActivityRequestHandler;
import com.tacoid.pweek.IGameService;
import com.tacoid.pweek.ScoreManager.GameType;
import com.tacoid.pweekmini.PweekMini;
import com.tacoid.tracking.TrackingManager;
import com.tacoid.tracking.TrackingManager.TrackerType;

public class Main implements IActivityRequestHandler, IGameService {
	private static Main application;

	public static void main(String[] args) {
		if(application == null) {
			application = new Main();
		}
		PweekMini.getInstance().setDesktopMode(true);
		TrackingManager.setTrackerType(TrackerType.DUMMY);
		PweekMini.setHandler(application);
		PweekMini.setGameService(application);
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "pweekmini";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 800;
		
		new LwjglApplication(PweekMini.getInstance(), cfg);
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
	public int getVolume() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return signedIn;
	}

	@Override
	public void submitScore(GameType type, int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showLeaderboard(LeaderboardType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showAchievements() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showFriendSelector() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockAchievement(Achievement a) {
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

	@Override
	public void getScoresData() {
		// TODO Auto-generated method stub
		
	}
}
