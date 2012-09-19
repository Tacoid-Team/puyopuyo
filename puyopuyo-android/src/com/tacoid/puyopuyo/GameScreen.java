package com.tacoid.puyopuyo;

import com.badlogic.gdx.Screen;

public interface GameScreen extends Screen {
	
	public void init();
	
	public void gamePause(); 
	
	public void gameResume();

}
