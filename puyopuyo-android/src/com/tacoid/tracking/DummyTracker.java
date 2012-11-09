package com.tacoid.tracking;

public class DummyTracker implements ITracker {

	@Override
	public void trackEvent(String category, String action, String label,
			Long value) {
		System.out.println("Tracking Event:");
		System.out.println("\tcategory="+category);
		System.out.println("\taction="+action);
		System.out.println("\tlabel="+label);
		System.out.println("\tvalue="+value);
	}

}
