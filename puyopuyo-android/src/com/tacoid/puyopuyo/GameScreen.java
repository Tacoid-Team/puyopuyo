package com.tacoid.puyopuyo;

import com.badlogic.gdx.Screen;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;

public interface GameScreen extends Screen {
	
	public void init();
	
	public void gamePause(); 
	
	public void gameResume();
	
	public ScreenOrientation getOrientation();
	
	public float getHeight();
	
	public float getWidth();

}
