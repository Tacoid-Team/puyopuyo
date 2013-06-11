package com.tacoid.pweek;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.tacoid.pweek.IActivityRequestHandler;
import com.tacoid.pweek.Pweek;
import com.tacoid.tracking.TrackingManager;
import com.tacoid.tracking.TrackingManager.TrackerType;

public class PweekDesktop implements IActivityRequestHandler, IGameService {
	private static PweekDesktop application;
	public static void main(String[] argv) {
		if(application == null) {
			application = new PweekDesktop();
		}
		Pweek.getInstance().setDesktopMode(true);
		TrackingManager.setTrackerType(TrackerType.DUMMY);
		Pweek.setHandler(application);
		new LwjglApplication(Pweek.getInstance(), "Pweek",  1280, 768, false);
	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPortrait(boolean isPortrait) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void Login() {
		System.out.println("GameService : Login");
		
	}

	@Override
	public void LogOut() {
		System.out.println("GameService : Logout");
		
	}

	@Override
	public boolean getSignedIn() {
		System.out.println("GameService : getSignedIn");
		return false;
	}

	@Override
	public void submitScore(int score) {
		System.out.println("GameService : submitScore");
		
	}

	@Override
	public void getScores() {
		System.out.println("GameService : getScores");
		
	}

	@Override
	public void getScoresData() {
		System.out.println("GameService : getScoresData");
	}
}