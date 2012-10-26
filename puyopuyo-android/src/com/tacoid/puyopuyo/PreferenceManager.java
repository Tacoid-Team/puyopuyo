package com.tacoid.puyopuyo;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferenceManager {
	/* Static part */
	static PreferenceManager instance = null;

	static public PreferenceManager getInstance() {
		if(instance == null) {
			instance = new PreferenceManager();
		}
		return instance;
	}
	/* ********** */
	
	Preferences prefs;
	private final String undefPref = "UNDEFINED";
	
	public enum Preference {
		/* Lister ici l'ensemble des langages supportés */
		LANGUAGE,
		SOUND_STATE,
		MUSIC_STATE;
	}
	
	public PreferenceManager() {
		prefs = Gdx.app.getPreferences("game_prefs");
		Map<java.lang.String,?> map = prefs.get();
		for(Preference pref : Preference.values()) {
			if(!map.containsKey(pref.toString())) {
				prefs.putString(pref.toString(), undefPref);
			}
		}
		
	}
	
	void setPreference(Preference pref, String value) {
		System.out.println("Writing preference ("+pref.toString()+"="+value+")");
		prefs.putString(pref.toString(), value);
		prefs.flush();
	}
	
	boolean isPreferenceDefined(Preference pref) {
		return !undefPref.equals(this.getPreference(pref));
	}
	
	String getPreference(Preference pref) {
		System.out.println("Reading preference ("+pref.toString()+"="+prefs.getString(pref.toString())+")");
		return prefs.getString(pref.toString());
	}
}
