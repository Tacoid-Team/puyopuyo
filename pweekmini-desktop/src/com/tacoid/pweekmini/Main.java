package com.tacoid.pweekmini;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tacoid.pweekmini.PweekMini;
import com.tacoid.tracking.TrackingManager;
import com.tacoid.tracking.TrackingManager.TrackerType;

public class Main implements IActivityRequestHandler {
	private static Main application;

	public static void main(String[] args) {
		if(application == null) {
			application = new Main();
		}
		PweekMini.getInstance().setDesktopMode(true);
		TrackingManager.setTrackerType(TrackerType.DUMMY);
		PweekMini.setHandler(application);
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "pweekmini";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 800;
		
		new LwjglApplication(PweekMini.getInstance(), cfg);
	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		
	}
}
