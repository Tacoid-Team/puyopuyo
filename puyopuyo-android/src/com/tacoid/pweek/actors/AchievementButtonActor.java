package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.Pweek;

public class AchievementButtonActor extends Button {

	private AchievementButtonActor(TextureRegion regionUp, TextureRegion regionDown,
			TextureRegion regionChecked) {
		super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown), new TextureRegionDrawable(regionChecked));
		this.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Pweek.getInstance().getGameService().showAchievements();
			}
		});
	}

	static public AchievementButtonActor createAchievementButton(TextureAtlas atlasButtons) {
		TextureRegion onRegion = new TextureRegion(atlasButtons.findRegion("achievement"));
		
		return new AchievementButtonActor(onRegion, onRegion, onRegion);
	}
}
