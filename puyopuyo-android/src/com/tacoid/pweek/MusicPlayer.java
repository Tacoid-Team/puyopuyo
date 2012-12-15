package com.tacoid.pweek;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.audio.Music;
import com.tacoid.pweek.PreferenceManager.Preference;
import com.tacoid.tracking.TrackingManager;

public class MusicPlayer {
	public static MusicPlayer instance = null;
	public static boolean initialized = false;
	
	static public enum MusicType {
		MAIN
	}
	
	private Map<MusicType, Music> musics;
	private float volume;
	private Music playing = null;
	private boolean muted = false;
	
	public void init(Music music) {
		musics = new HashMap<MusicType, Music>();
		playing = null;
		
		musics.put(MusicType.MAIN, music);
		
		if(!PreferenceManager.getInstance().isPreferenceDefined(Preference.MUSIC_STATE)) {
			PreferenceManager.getInstance().setPreference(Preference.MUSIC_STATE, "on");
		} else {
			if(PreferenceManager.getInstance().getPreference(Preference.MUSIC_STATE).equals("off")) {
				muted = true;
			}
		}
	}
	
	private MusicPlayer() {
	}
	
	
	public static MusicPlayer getInstance() {
		if (instance == null) {
			instance = new MusicPlayer();
		}
		return instance;
	}

	
	public void playMusic(MusicType music, boolean looping) {
		Music m = musics.get(music);
		
		if(playing != null && playing.isPlaying()) {
			playing.stop();
		}
		
		if(muted) {
			m.setVolume(0.0f);
		} else {
			m.setVolume(volume);
		}
		
		m.setLooping(looping);
		m.play();
		
		playing = m;
		
	}
	
	public void stopMusic() {
		playing.stop();
	}
	
	public void setVolume(float volume) {
		this.volume = volume;
		if(playing != null && playing.isPlaying() && !muted) {
			playing.setVolume(volume);
		}
	}
	
	public void mute() {
		muted = true;
		if(playing != null && playing.isPlaying()) {
			playing.setVolume(0.0f);
		}
		TrackingManager.getTracker().trackEvent("UI", "sound", "music mute", null);
		PreferenceManager.getInstance().setPreference(Preference.MUSIC_STATE, "off");
	}
	
	public void unmute() {
		muted = false;
		if(playing != null && playing.isPlaying()) {
			playing.setVolume(volume);
		}
		TrackingManager.getTracker().trackEvent("UI", "sound", "music unmute", null);
		PreferenceManager.getInstance().setPreference(Preference.MUSIC_STATE, "on");
	}
	
	public boolean isMuted() {
		return muted;
	}
}