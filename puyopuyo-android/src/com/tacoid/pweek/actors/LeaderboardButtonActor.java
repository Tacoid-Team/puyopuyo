package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.IGameService.LeaderboardType;
import com.tacoid.pweek.Pweek;

public class LeaderboardButtonActor extends Button {
	
	private LeaderboardButtonActor(TextureRegion regionUp, TextureRegion regionDown,
			TextureRegion regionChecked) {
		super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown), new TextureRegionDrawable(regionChecked));
	}

	static public LeaderboardButtonActor createLeaderboardButton(TextureAtlas atlasButtons, final LeaderboardType type) {
		TextureRegion onRegion = new TextureRegion(atlasButtons.findRegion("leaderboard"));
		
		LeaderboardButtonActor instance = new LeaderboardButtonActor(onRegion, onRegion, onRegion);
		
		instance.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Pweek.getInstance().getGameService().showLeaderboard(type);
			}
		});
		
		return instance;
	}
}
