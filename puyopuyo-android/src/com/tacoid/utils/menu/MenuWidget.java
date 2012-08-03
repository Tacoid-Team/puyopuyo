package com.tacoid.utils.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class MenuWidget extends Actor{
	
	public void setX(float X) {
		this.x = X;
	}
	
	public void setY(float Y) {
		this.y = Y;
	}
	
	public void setWidth(float W) {
		this.width = W;
	}
	
	public void setHeight(float W) {
		this.width = W;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.width;
	}

}
