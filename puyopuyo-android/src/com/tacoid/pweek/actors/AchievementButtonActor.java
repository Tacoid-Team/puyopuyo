package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.SoundPlayer;

public class AchievementButtonActor extends Button {

	private AchievementButtonActor(TextureRegion regionUp, TextureRegion regionDown,
			TextureRegion regionChecked) {
		super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown), new TextureRegionDrawable(regionChecked));
		this.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Pweek.getInstance().getGameService().getSignedIn()) {
					System.out.println("Signed in");
					Pweek.getInstance().getGameService().showAchievements();
				} else {
					System.out.println("FAIL");
				}
			}
		});
	}

	static public AchievementButtonActor createAchievementButton(TextureAtlas atlasButtons) {
		TextureRegion onRegion = new TextureRegion(atlasButtons.findRegion("sound-on"));
		
		return new AchievementButtonActor(onRegion, onRegion, onRegion);
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
