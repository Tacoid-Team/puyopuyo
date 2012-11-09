package com.tacoid.tracking;


public class TrackingManager {
	
	public enum TrackerType {
		GOOGLE_ANALYTICS,
		DUMMY
	};
	
	private static ITracker tracker = null;
	
	public static void setTrackerType(TrackerType type) {
		switch(type) {
		case GOOGLE_ANALYTICS:
			tracker = new GoogleAnalyticsTracker();
			break;
		case DUMMY:
			tracker = new DummyTracker();
		}
	}
	
	public static ITracker getTracker() {
		return tracker;
	}
	
}
