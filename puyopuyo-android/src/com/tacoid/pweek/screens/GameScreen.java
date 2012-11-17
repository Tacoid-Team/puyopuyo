package com.tacoid.pweek.screens;

import com.badlogic.gdx.Screen;
import com.tacoid.pweek.Pweek.ScreenOrientation;
import com.tacoid.pweek.ScoreManager.GameType;

public interface GameScreen extends Screen {
	
	public void init();
	
	public void gamePause(); 
	
	public void gameResume();
	
	public ScreenOrientation getOrientation();
	
	public float getHeight();
	
	public float getWidth();
	
	public GameType getGameType();
	
	public int getScore();
	
	public int getLevel();
	
	public float getElapsedTime();

}
