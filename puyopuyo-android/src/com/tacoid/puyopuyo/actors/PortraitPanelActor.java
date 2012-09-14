package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.PuyoPuyo;

public class PortraitPanelActor extends Actor {
	private static final int VIRTUAL_HEIGHT= 768;
	private Sprite mainPanel;
	private Sprite topPanel;
	private Sprite leftPanel;
	private Sprite mainPanelIA;
	private Sprite topPanelIA;
	private Sprite leftPanelIA;
	
	public PortraitPanelActor() {
		TextureRegion mainPanelRegion = PuyoPuyo.getInstance().atlasPanelsPortrait.findRegion("main-panel");
		TextureRegion leftPanelRegion = PuyoPuyo.getInstance().atlasPanelsPortrait.findRegion("left-panel");
		TextureRegion topPanelRegion =  PuyoPuyo.getInstance().atlasPanelsPortrait.findRegion("top-panel");

		mainPanel = new Sprite(mainPanelRegion);
		topPanel = new Sprite(topPanelRegion);
		leftPanel = new Sprite(leftPanelRegion);
		
		mainPanel.setPosition(280, 300);
		topPanel.setPosition(440,1190);
		leftPanel.setPosition(40,860);
	}
	
	@Override
	public void draw(SpriteBatch batch, float arg1) {
		mainPanel.draw(batch);
		topPanel.draw(batch);
		leftPanel.draw(batch);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}
}