package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.tacoid.pweek.MusicPlayer;
import com.tacoid.pweek.Pweek;

public class MusicButtonActor extends Button implements ClickListener{

	private MusicButtonActor(TextureRegion regionUp, TextureRegion regionDown,
			TextureRegion regionChecked) {
		super(regionUp, regionDown, regionChecked);
		setChecked(MusicPlayer.getInstance().isMuted());
		setClickListener(this);
	}
	
	static public MusicButtonActor createMusicButton() {
		TextureRegion musicOnRegion = new TextureRegion(Pweek.getInstance().atlasBouttons.findRegion("music-on"));
		TextureRegion musicOffRegion = new TextureRegion(Pweek.getInstance().atlasBouttons.findRegion("music-off"));
		
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
	public void draw (SpriteBatch batch, float parentAlpha) {
		if(MusicPlayer.getInstance().isMuted()) {
			this.setChecked(true);
		} else {
			this.setChecked(false);
		}
		super.draw(batch, parentAlpha);
		
	}
	

}
