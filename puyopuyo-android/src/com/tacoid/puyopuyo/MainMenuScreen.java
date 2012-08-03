package com.tacoid.puyopuyo;

import com.badlogic.gdx.Gdx;

import com.tacoid.utils.menu.Menu;
import com.tacoid.utils.menu.MenuButton;


public class MainMenuScreen extends Menu {
	
	private static MainMenuScreen instance = null;
	
	public MainMenuScreen() {
		this.addWidget(new PlayButton(), 10, 450 );
		this.addWidget(new ExitButton(), 10, 380 );

	}
	
	private class PlayButton extends MenuButton{
		
		public PlayButton() {
			super("images/menu/play.png",  124, 124);
		}
	
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			PuyoPuyo.getInstance().setScreen(GameScreen.getInstance());
			return true;
		}
	}

	private class ExitButton extends MenuButton{
	
		public ExitButton() {
			super("images/menu/exit.png",  124, 124);
		}
	
		@Override
		public boolean touchDown(float x, float y, int pointer) {
			Gdx.app.exit();
			return true;
		}
	}

	static public MainMenuScreen getInstance()
	{
			if (instance == null) {
				instance = new MainMenuScreen();
			}
			return instance;
	}
	

}