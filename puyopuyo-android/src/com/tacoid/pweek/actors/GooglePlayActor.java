package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.IGameLoginListener;
import com.tacoid.pweek.IGameService;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.SoundPlayer;
import com.tacoid.pweek.IGameService.LeaderboardType;
import com.tacoid.pweek.SoundPlayer.SoundType;

public class GooglePlayActor extends Group implements IGameLoginListener {

	
	public class AchievementButtonActor extends Button {
		public AchievementButtonActor(TextureRegion regionUp, TextureRegion regionDown,
				TextureRegion regionChecked) {
			super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown), new TextureRegionDrawable(regionChecked));
			this.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
					Pweek.getInstance().getGameService().showAchievements();
				}
			});
		}
	}

	public class LeaderboardButtonActor extends Button {
		private LeaderboardButtonActor(final LeaderboardType type, TextureRegion regionUp, TextureRegion regionDown,
				TextureRegion regionChecked) {
			super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown), new TextureRegionDrawable(regionChecked));
			
			this.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
					Pweek.getInstance().getGameService().showLeaderboard(type);
				}
			});
		}
	}
	
	public class LogoutButtonActor extends Button {
		private LogoutButtonActor(TextureRegion regionUp, TextureRegion regionDown,
				TextureRegion regionChecked) {
			super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown), new TextureRegionDrawable(regionChecked));
			
			this.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
					Pweek.getInstance().getGameService().logout();
				}
			});
		}
	}

	private final IGameService gs;
	private final LeaderboardType type;
	private final TextureAtlas atlasButtons;
	private final TextureAtlas atlasGoogle;
	private boolean isSignedIn;
	
	public GooglePlayActor(IGameService gs, LeaderboardType type, TextureAtlas atlasButtons, TextureAtlas atlasGoogle) {
		this.gs = gs;
		this.type = type;
		this.atlasButtons = atlasButtons;
		this.atlasGoogle = atlasGoogle;
		this.isSignedIn = gs.getSignedIn(); 
		
		if (isSignedIn) {
			login();
		} else {
			logout();
		}
	}
	
	@Override
	public void act(float arg0) {
		super.act(arg0);
		
		boolean cur = gs.getSignedIn();
		if (isSignedIn && !cur) {
			logout();
		} else if (!isSignedIn && cur) {
			login();
		}
		isSignedIn = cur;
	}

	@Override
	public void login() {
		clear();
		
		
		if (type == LeaderboardType.ALL) {
			TextureRegion onRegionA = new TextureRegion(atlasButtons.findRegion("achievement"));
			addActor(new AchievementButtonActor(onRegionA, onRegionA, onRegionA));
		}
		
		TextureRegion onRegionL = new TextureRegion(atlasButtons.findRegion("leaderboard"));
		LeaderboardButtonActor l = new LeaderboardButtonActor(type, onRegionL, onRegionL, onRegionL);
		if (type == LeaderboardType.ALL) {
			l.setX(90);
		}
		
		if (type == LeaderboardType.ALL) {
			TextureRegion onRegionLogout = new TextureRegion(atlasButtons.findRegion("logout_button"));
			LogoutButtonActor logoutActor = new LogoutButtonActor(onRegionLogout, onRegionLogout, onRegionLogout);
			logoutActor.setX(180);
			addActor(logoutActor);
		}
		
		addActor(l);
	}

	@Override
	public void logout() {
		clear();
		
		addActor(SignInButton.create(gs, atlasGoogle, false, type != LeaderboardType.ALL));		
	}	
}
