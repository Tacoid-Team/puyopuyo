package com.tacoid.puyopuyo;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {
	static public MusicPlayer instance = null;
	
	static public enum MusicType {
		MAIN
	}
	
	private Map<MusicType, Music> musics;
	private float volume;
	private Music playing = null;
	private boolean muted = false;
	
	private MusicPlayer() {
		musics = new HashMap<MusicType, Music>();
		
		musics.put(MusicType.MAIN, Gdx.audio.newMusic(Gdx.files.internal("sounds/AnoyingMusic.mp3")));
	}
	
	public static MusicPlayer getInstance() {
		if(instance == null) {
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
	}
	
	public void unmute() {
		muted = false;
		if(playing != null && playing.isPlaying()) {
			playing.setVolume(volume);
		}
	}
	
	public boolean isMuted() {
		return muted;
	}
}