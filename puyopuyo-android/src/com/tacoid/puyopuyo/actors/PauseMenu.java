package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.tacoid.puyopuyo.MainMenuScreen;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;
import com.tacoid.puyopuyo.logic.GameLogic;

public class PauseMenu extends Group{
	
	private GameLogic playerLogic;
	private GameLogic iaLogic;
	
	private SwingMenu menu;
	
	private class ContinueButton extends Button{
		public ContinueButton(TextureRegion region) {
			super(region);
			x = 100;
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

		public void touchUp(float x, float y, int pointer) {
			playerLogic.init();
			if(iaLogic != null) {
				iaLogic.init();
			}
			hide();
			PuyoPuyo.getInstance().setScreen(MainMenuScreen.getInstance());
		}
	}
	
	public PauseMenu(GameLogic player, GameLogic ia, ScreenOrientation orientation) {
		TextureRegion continueRegion = PuyoPuyo.getInstance().atlasPlank.findRegion("continuer-fr");
		TextureRegion quitRegion = PuyoPuyo.getInstance().atlasPlank.findRegion("quitter-fr");
		
		menu = new SwingMenu(orientation);
		
		menu.initBegin();
		menu.addButton(new ContinueButton(continueRegion));
		menu.addButton(new QuitButton(quitRegion));
		menu.initEnd();
		menu.hide();
		
		this.addActor(menu);
		
		playerLogic = player;
		iaLogic = ia;
		visible = false;
	}
	
	public void show() {
		playerLogic.pause();
		
		if(iaLogic != null) {
			iaLogic.pause();
		}
		this.visible = true;
		menu.show();
	}
	
	public void hide() {
		playerLogic.resume();
		
		if(iaLogic != null) {
			iaLogic.resume();
		}
		this.visible = false;
		menu.hide();
	}
}
