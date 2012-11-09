package com.tacoid.tracking;

public interface ITracker {

	void trackEvent(String category, String action, String label, Long value);
	
	/* TODO: Ajouter les autres interfaces quand on en aura besoin, j'ai la flème dans l'immédiat */
}
