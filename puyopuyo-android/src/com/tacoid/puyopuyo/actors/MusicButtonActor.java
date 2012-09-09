package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.tacoid.puyopuyo.MusicPlayer;
import com.tacoid.puyopuyo.PuyoPuyo;

public class MusicButtonActor extends Button implements ClickListener{

	private MusicButtonActor(TextureRegion regionUp, TextureRegion regionDown,
			TextureRegion regionChecked) {
		super(regionUp, regionDown, regionChecked);
		setChecked(MusicPlayer.getInstance().isMuted());
		setClickListener(this);
	}
	
	static public MusicButtonActor createMusicButton() {
		TextureRegion musicOnRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/music-on.png",Texture.class), 32, 32);
		TextureRegion musicOffRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/music-off.png",Texture.class), 32, 32);
		
		return new MusicButtonActor(musicOnRegion, musicOnRegion, musicOffRegion);
	}

	@Override
	public void click(Actor arg0, float arg1, float arg2) {
		if(this.isChecked()) {
			MusicPlayer.getInstance().mute();
		} else {
			MusicPlayer.getInstance().unmute();
		}
	}
	

}
