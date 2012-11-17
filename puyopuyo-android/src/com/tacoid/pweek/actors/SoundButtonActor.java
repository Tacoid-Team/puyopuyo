package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.SoundPlayer;

public class SoundButtonActor extends Button implements ClickListener{

	private SoundButtonActor(TextureRegion regionUp, TextureRegion regionDown,
			TextureRegion regionChecked) {
		super(regionUp, regionDown, regionChecked);
		setChecked(SoundPlayer.getInstance().isMuted());
		this.
		setClickListener(this);
	}
	
	
	static public SoundButtonActor createSoundButton() {
		TextureRegion musicOnRegion = new TextureRegion(Pweek.getInstance().atlasBouttons.findRegion("sound-on"));
		TextureRegion musicOffRegion = new TextureRegion(Pweek.getInstance().atlasBouttons.findRegion("sound-off"));
		
		return new SoundButtonActor(musicOnRegion, musicOnRegion, musicOffRegion);
	}

	@Override
	public void click(Actor arg0, float arg1, float arg2) {
		if(this.isChecked()) {
			SoundPlayer.getInstance().mute();
		} else {
			SoundPlayer.getInstance().unmute();
		}
	}
	public void draw (SpriteBatch batch, float parentAlpha) {
		if(SoundPlayer.getInstance().isMuted()) {
			this.setChecked(true);
		} else {
			this.setChecked(false);
		}
		super.draw(batch, parentAlpha);
		
	}
	

}
