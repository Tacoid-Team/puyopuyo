package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.SoundPlayer;

public class SoundButtonActor extends Button {

	private SoundButtonActor(TextureRegion regionUp, TextureRegion regionDown,
			TextureRegion regionChecked) {
		super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown), new TextureRegionDrawable(regionChecked));
		setChecked(SoundPlayer.getInstance().isMuted());
		this.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(isChecked()) {
					SoundPlayer.getInstance().mute();
				} else {
					SoundPlayer.getInstance().unmute();
				}
			}
		});
	}

	static public SoundButtonActor createSoundButton(TextureAtlas atlasButtons) {
		TextureRegion musicOnRegion = new TextureRegion(atlasButtons.findRegion("sound-on"));
		TextureRegion musicOffRegion = new TextureRegion(atlasButtons.findRegion("sound-off"));
		
		return new SoundButtonActor(musicOnRegion, musicOnRegion, musicOffRegion);
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
