package com.tacoid.puyopuyo;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.audio.Music;
import com.tacoid.puyopuyo.PreferenceManager.Preference;

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
	
	private MusicPlayer() {
		musics = new HashMap<MusicType, Music>();
		playing = null;
		
		musics.put(MusicType.MAIN, PuyoPuyo.getInstance().manager.get("sounds/AnoyingMusic.mp3", Music.class));
		
		if(!PreferenceManager.getInstance().isPreferenceDefined(Preference.MUSIC_STATE)) {
			PreferenceManager.getInstance().setPreference(Preference.MUSIC_STATE, "on");
		} else {
			if(PreferenceManager.getInstance().getPreference(Preference.MUSIC_STATE).equals("off")) {
				muted = true;
			}
		}
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
		PreferenceManager.getInstance().setPreference(Preference.MUSIC_STATE, "off");
	}
	
	public void unmute() {
		muted = false;
		if(playing != null && playing.isPlaying()) {
			playing.setVolume(volume);
		}
		PreferenceManager.getInstance().setPreference(Preference.MUSIC_STATE, "on");
	}
	
	public boolean isMuted() {
		return muted;
	}
}