package com.tacoid.tracking;

import com.badlogic.gdx.Gdx;
import com.google.analytics.tracking.android.EasyTracker;

public class GoogleAnalyticsTracker implements ITracker {

	@Override
	public void trackEvent(String category, String action, String label,
			Long value) {
		EasyTracker.getTracker().trackEvent(category, action, label, value);
	}

}
