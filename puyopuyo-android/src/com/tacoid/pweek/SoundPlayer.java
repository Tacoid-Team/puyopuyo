package com.tacoid.pweek;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.tacoid.pweek.PreferenceManager.Preference;
import com.tacoid.tracking.TrackingManager;

public class SoundPlayer {
	static public SoundPlayer instance = null;
	
	static public enum SoundType {
		ROTATE,
		MOVE,
		FALL,
		EXPLODE,
		TOUCH_MENU,
		GARBAGE,
		WIN,
		LOSE,
		NUISANCE
	}
	
	private boolean muted = false;
	private Map<SoundType, Sound> sounds;
	
	public void init(AssetManager manager) {
		sounds = new HashMap<SoundType, Sound>();
		
		sounds.put(SoundType.ROTATE, manager.get("sounds/bleep2.wav", Sound.class));
		sounds.put(SoundType.MOVE, manager.get("sounds/bleep.wav", Sound.class));
		sounds.put(SoundType.FALL, manager.get("sounds/bleep.wav", Sound.class));
		sounds.put(SoundType.EXPLODE, manager.get("sounds/explode.wav", Sound.class));
		sounds.put(SoundType.TOUCH_MENU, manager.get("sounds/click.wav", Sound.class));
		sounds.put(SoundType.GARBAGE, manager.get("sounds/bleep.wav", Sound.class));
		sounds.put(SoundType.WIN, manager.get("sounds/bleep.wav", Sound.class));
		sounds.put(SoundType.LOSE, manager.get("sounds/bleep.wav", Sound.class));
		sounds.put(SoundType.NUISANCE, manager.get("sounds/nuisance.wav", Sound.class));
		
		if(!PreferenceManager.getInstance().isPreferenceDefined(Preference.SOUND_STATE)) {
			PreferenceManager.getInstance().setPreference(Preference.SOUND_STATE, "on");
		} else {
			if(PreferenceManager.getInstance().getPreference(Preference.SOUND_STATE).equals("off")) {
				muted = true;
			}
		}
	}
	
	private SoundPlayer() {
		
		
	}
	
	public static SoundPlayer getInstance() {
		if(instance == null) {
			instance = new SoundPlayer();
		}
		return instance;
	}

	
	public void playSound(SoundType sound, float volume, boolean randomize) {
		Sound s = sounds.get(sound);
		long id;
		
		id = s.play(muted?0:volume);
		if(randomize)
			s.setPitch(id, (float) (1.0f+(Math.random()*0.1 - 0.05)));
		
	}
	
	public void playSound(SoundType sound, float volume, float pitch) {
		Sound s = sounds.get(sound);
		long id;
		
		id = s.play(muted?0:volume);
		s.setPitch(id, pitch);
		
	}
	
	public void mute() {
		muted = true;
		TrackingManager.getTracker().trackEvent("UI", "sound", "sound mute", null);
		PreferenceManager.getInstance().setPreference(Preference.SOUND_STATE, "off");
	}
	
	public void unmute() {
		muted = false;
		TrackingManager.getTracker().trackEvent("UI", "sound", "sound unmute", null);
		PreferenceManager.getInstance().setPreference(Preference.SOUND_STATE, "on");
	}
	
	public boolean isMuted() {
		return muted;
	}
}
