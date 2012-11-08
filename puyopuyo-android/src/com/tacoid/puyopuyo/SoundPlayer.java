package com.tacoid.puyopuyo;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.audio.Sound;
import com.google.analytics.tracking.android.EasyTracker;
import com.tacoid.puyopuyo.PreferenceManager.Preference;

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
	
	private SoundPlayer() {
		sounds = new HashMap<SoundType, Sound>();
		
		sounds.put(SoundType.ROTATE, PuyoPuyo.getInstance().manager.get("sounds/bleep2.wav", Sound.class));
		sounds.put(SoundType.MOVE, PuyoPuyo.getInstance().manager.get("sounds/bleep.wav", Sound.class));
		sounds.put(SoundType.FALL, PuyoPuyo.getInstance().manager.get("sounds/bleep.wav", Sound.class));
		sounds.put(SoundType.EXPLODE, PuyoPuyo.getInstance().manager.get("sounds/explode.wav", Sound.class));
		sounds.put(SoundType.TOUCH_MENU, PuyoPuyo.getInstance().manager.get("sounds/click.wav", Sound.class));
		sounds.put(SoundType.GARBAGE, PuyoPuyo.getInstance().manager.get("sounds/bleep.wav", Sound.class));
		sounds.put(SoundType.WIN, PuyoPuyo.getInstance().manager.get("sounds/bleep.wav", Sound.class));
		sounds.put(SoundType.LOSE, PuyoPuyo.getInstance().manager.get("sounds/bleep.wav", Sound.class));
		sounds.put(SoundType.NUISANCE, PuyoPuyo.getInstance().manager.get("sounds/nuisance.wav", Sound.class));
		
		if(!PreferenceManager.getInstance().isPreferenceDefined(Preference.SOUND_STATE)) {
			PreferenceManager.getInstance().setPreference(Preference.SOUND_STATE, "on");
		} else {
			if(PreferenceManager.getInstance().getPreference(Preference.SOUND_STATE).equals("off")) {
				muted = true;
			}
		}
		
		
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
		EasyTracker.getTracker().trackEvent("UI", "sound", "sound mute", null);
		PreferenceManager.getInstance().setPreference(Preference.SOUND_STATE, "off");
	}
	
	public void unmute() {
		muted = false;
		EasyTracker.getTracker().trackEvent("UI", "sound", "sound unmute", null);
		PreferenceManager.getInstance().setPreference(Preference.SOUND_STATE, "on");
	}
	
	public boolean isMuted() {
		return muted;
	}
}
