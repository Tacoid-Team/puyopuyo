package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PortraitPanelActor extends Actor {
	private Sprite mainPanel;
	private Sprite topPanel;
	private Sprite leftPanel;
	private Sprite leftPanel2;
	
	public PortraitPanelActor(TextureAtlas atlasPanelsPortrait) {
		TextureRegion mainPanelRegion = atlasPanelsPortrait.findRegion("main-panel");
		TextureRegion leftPanelRegion = atlasPanelsPortrait.findRegion("left-panel");
		TextureRegion topPanelRegion =  atlasPanelsPortrait.findRegion("top-panel");

		mainPanel = new Sprite(mainPanelRegion);
		topPanel = new Sprite(topPanelRegion);
		leftPanel = new Sprite(leftPanelRegion);
		leftPanel2 = new Sprite(topPanelRegion);
		
		mainPanel.setPosition(280, 300);
		topPanel.setPosition(320, 1190);
		leftPanel.setPosition(40, 830);
		leftPanel2.setPosition(20, 750);
	}
	
	@Override
	public void draw(SpriteBatch batch, float arg1) {
		mainPanel.draw(batch);
		topPanel.draw(batch);
		leftPanel.draw(batch);
		leftPanel2.draw(batch);
	}

	@Override
	public Actor hit(float arg0, float arg1, boolean touchable) {
		// TODO Auto-generated method stub
		return null;
	}
}