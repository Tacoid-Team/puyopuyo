package com.tacoid.puyopuyo.screens;

import com.badlogic.gdx.Screen;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.ScoreManager;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;
import com.tacoid.puyopuyo.ScoreManager.GameType;

public interface GameScreen extends Screen {
	
	public void init();
	
	public void gamePause(); 
	
	public void gameResume();
	
	public ScreenOrientation getOrientation();
	
	public float getHeight();
	
	public float getWidth();
	
	public GameType getGameType();
	
	public int getScore();

}
