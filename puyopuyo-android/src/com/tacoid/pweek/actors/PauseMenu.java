package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.SoundPlayer;
import com.tacoid.pweek.Pweek.ScreenOrientation;
import com.tacoid.pweek.SoundPlayer.SoundType;
import com.tacoid.pweek.screens.GameScreen;

public class PauseMenu extends Group{
	
	private GameScreen gameScreen;
	
	private SwingMenu menu;
	
	private class ContinueButton extends Button {
		public ContinueButton(TextureRegion region) {
			super(new TextureRegionDrawable(region));
			setX(100);
			addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					Pweek.getInstance().getHandler().showAds(false);
					hide();
				}
			});
		}
	}
	
	private class QuitButton extends Button{
		public QuitButton(TextureRegion region) {
			super(new TextureRegionDrawable(region));
			setX(800);
			addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
					return true;
				}
				
				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					gameScreen.quit();
				}
			});
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
		setVisible(true);
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
