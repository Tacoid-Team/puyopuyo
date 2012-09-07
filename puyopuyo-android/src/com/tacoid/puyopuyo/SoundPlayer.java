package com.tacoid.puyopuyo;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

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
		LOSE
	}
	
	private Map<SoundType, Sound> sounds;
	
	private SoundPlayer() {
		sounds = new HashMap<SoundType, Sound>();
		
		sounds.put(SoundType.ROTATE, Gdx.audio.newSound(Gdx.files.internal("sounds/bleep2.wav")));
		sounds.put(SoundType.MOVE, Gdx.audio.newSound(Gdx.files.internal("sounds/bleep.wav")));
		sounds.put(SoundType.FALL, Gdx.audio.newSound(Gdx.files.internal("sounds/bleep.wav")));
		sounds.put(SoundType.EXPLODE, Gdx.audio.newSound(Gdx.files.internal("sounds/explode.wav")));
		sounds.put(SoundType.TOUCH_MENU, Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav")));
		sounds.put(SoundType.GARBAGE, Gdx.audio.newSound(Gdx.files.internal("sounds/bleep.wav")));
		sounds.put(SoundType.WIN, Gdx.audio.newSound(Gdx.files.internal("sounds/bleep.wav")));
		sounds.put(SoundType.LOSE, Gdx.audio.newSound(Gdx.files.internal("sounds/bleep.wav")));
		
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
		
		id = s.play(volume);
		if(randomize)
			s.setPitch(id, (float) (1.0f+(Math.random()*0.1 - 0.05)));
		
	}
}
