package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.MusicPlayer;

public class MusicButtonActor extends Button {

	private MusicButtonActor(TextureRegion regionUp, TextureRegion regionDown,
			TextureRegion regionChecked) {
		super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown), new TextureRegionDrawable(regionChecked));
		setChecked(MusicPlayer.getInstance().isMuted());
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(isChecked()) {
					MusicPlayer.getInstance().mute();
				} else {
					MusicPlayer.getInstance().unmute();
				}
			}
		});
	}
	
	static public MusicButtonActor createMusicButton(TextureAtlas atlasButtons) {
		TextureRegion musicOnRegion = new TextureRegion(atlasButtons.findRegion("music-on"));
		TextureRegion musicOffRegion = new TextureRegion(atlasButtons.findRegion("music-off"));
		
		return new MusicButtonActor(musicOnRegion, musicOnRegion, musicOffRegion);
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
