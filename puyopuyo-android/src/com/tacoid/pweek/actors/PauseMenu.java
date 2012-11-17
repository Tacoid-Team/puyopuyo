package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.SoundPlayer;
import com.tacoid.pweek.Pweek.ScreenOrientation;
import com.tacoid.pweek.ScoreManager.GameType;
import com.tacoid.pweek.SoundPlayer.SoundType;
import com.tacoid.pweek.screens.GameScreen;
import com.tacoid.pweek.screens.MainMenuScreen;
import com.tacoid.tracking.TrackingManager;
public class PauseMenu extends Group{
	
	private GameScreen gameScreen;
	
	private SwingMenu menu;
	
	private class ContinueButton extends Button{
		public ContinueButton(TextureRegion region) {
			super(region);
			x = 100;
		}
		
		public boolean touchDown(float x, float y, int pointer) {
			SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
			return true;
		}

		public void touchUp(float x, float y, int pointer) {
			Pweek.getInstance().getHandler().showAds(false);
			hide();
		}
	}
	
	private class QuitButton extends Button{
		public QuitButton(TextureRegion region) {
			super(region);
			x = 800;
		}

		public boolean touchDown(float x, float y, int pointer) {
			SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
			return true;
		}
		
		public void touchUp(float x, float y, int pointer) {
			hide();
			GameType type = gameScreen.getGameType();
			if(type == GameType.VERSUS_IA) {
				TrackingManager.getTracker().trackEvent("UI", "button_click",type.toString()+" Level "+ gameScreen.getLevel() +" quit before end", null);
			} else {
				TrackingManager.getTracker().trackEvent("UI", "button_click",type.toString() + " quit before end", null);
			}
			Pweek.getInstance().getHandler().setPortrait(false);
			Pweek.getInstance().setScreen(MainMenuScreen.getInstance());
		}
	}
	
	public PauseMenu(GameScreen gameScreen, ScreenOrientation orientation, boolean hidden) {
		TextureRegion continueRegion = Pweek.getInstance().atlasPlank.findRegion("continuer");
		TextureRegion quitRegion = Pweek.getInstance().atlasPlank.findRegion("quitter");
		
		menu = new SwingMenu(orientation);
		
		menu.initBegin("pause");
		menu.addButton(new ContinueButton(continueRegion));
		menu.addButton(new QuitButton(quitRegion));
		menu.initEnd();
		if (hidden) {
			menu.hide();
		}
		
		this.addActor(menu);
		
		this.gameScreen = gameScreen;
		visible = true;
	}
	
	public void show() {
		Pweek.getInstance().getHandler().showAds(true);
		gameScreen.gamePause();
		menu.show("pause");
	}
	
	public void hide() {
		gameScreen.gameResume();
		menu.hideInstant();
	}
}
