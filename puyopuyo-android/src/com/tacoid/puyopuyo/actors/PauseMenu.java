package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.tacoid.puyopuyo.GameScreen;
import com.tacoid.puyopuyo.MainMenuScreen;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.SoundPlayer;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;
import com.tacoid.puyopuyo.SoundPlayer.SoundType;
public class PauseMenu extends Group{
	
	private GameScreen gameScreen;
	
	private SwingMenu menu;
	
	private class ContinueButton extends Button{
		public ContinueButton(TextureRegion region) {
			super(region);
			x = 100;
		}
		
		public boolean touchDown(float x, float y, int pointer) {
			SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 1.0f, true);
			return true;
		}

		public void touchUp(float x, float y, int pointer) {
			hide();
		}
	}
	
	private class QuitButton extends Button{
		public QuitButton(TextureRegion region) {
			super(region);
			x = 800;
		}

		public boolean touchDown(float x, float y, int pointer) {
			SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 1.0f, true);
			return true;
		}
		
		public void touchUp(float x, float y, int pointer) {
			hide();
			PuyoPuyo.getInstance().setScreen(MainMenuScreen.getInstance());
		}
	}
	
	public PauseMenu(GameScreen gameScreen, ScreenOrientation orientation) {
		TextureRegion continueRegion = PuyoPuyo.getInstance().atlasPlank.findRegion("continuer-fr");
		TextureRegion quitRegion = PuyoPuyo.getInstance().atlasPlank.findRegion("quitter-fr");
		
		menu = new SwingMenu(orientation);
		
		menu.initBegin();
		menu.addButton(new ContinueButton(continueRegion));
		menu.addButton(new QuitButton(quitRegion));
		menu.initEnd();
		menu.hide();
		
		this.addActor(menu);
		
		this.gameScreen = gameScreen;
		visible = false;
	}
	
	public void show() {
		gameScreen.gamePause();
		this.visible = true;
		menu.show();
	}
	
	public void hide() {
		gameScreen.gameResume();
		this.visible = false;
		menu.hide();
	}
}
