package com.tacoid.puyopuyo;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.tacoid.tracking.TrackingManager;
import com.tacoid.tracking.TrackingManager.TrackerType;

public class PuyoPuyoDesktop implements IActivityRequestHandler {
	private static PuyoPuyoDesktop application;
	public static void main(String[] argv) {
		if(application == null) {
			application = new PuyoPuyoDesktop();
		}
		PuyoPuyo.getInstance().setDesktopMode(true);
		TrackingManager.setTrackerType(TrackerType.DUMMY);
		PuyoPuyo.setHandler(application);
		new LwjglApplication(PuyoPuyo.getInstance(), "Puyo Puyo",  1280, 768, false);
	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPortrait(boolean isPortrait) {
		// TODO Auto-generated method stub
		
	}
}