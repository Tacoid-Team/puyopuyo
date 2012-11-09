package com.tacoid.puyopuyo;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.tacoid.tracking.TrackingManager;
import com.tacoid.tracking.TrackingManager.TrackerType;

public class PuyoPuyoDesktop {
	public static void main(String[] argv) {
		PuyoPuyo.getInstance().setDesktopMode(true);
		TrackingManager.setTrackerType(TrackerType.DUMMY);
		new LwjglApplication(PuyoPuyo.getInstance(), "Puyo Puyo",  1280, 768, false);
	}
}