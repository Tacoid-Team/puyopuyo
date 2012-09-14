package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.PuyoPuyo;

public class LandscapePanelActor extends Actor {
	private static final int VIRTUAL_HEIGHT= 768;
	private static final int VIRTUAL_WIDTH = 1280;
	private Sprite mainPanel;
	private Sprite topPanel;
	private Sprite leftPanel;
	private Sprite mainPanelIA;
	private Sprite topPanelIA;
	private Sprite leftPanelIA;
	
	public LandscapePanelActor() {
		TextureRegion mainPanelRegion = PuyoPuyo.getInstance().atlasPanelsLandscape.findRegion("main-panel");
		TextureRegion leftPanelRegion = PuyoPuyo.getInstance().atlasPanelsLandscape.findRegion("left-panel");
		TextureRegion topPanelRegion =  PuyoPuyo.getInstance().atlasPanelsLandscape.findRegion("top-panel");

		mainPanel = new Sprite(mainPanelRegion);
		mainPanelIA = new Sprite(mainPanelRegion);
		topPanel = new Sprite(topPanelRegion);
		topPanelIA = new Sprite(topPanelRegion);
		leftPanel = new Sprite(leftPanelRegion);
		leftPanelIA = new Sprite(leftPanelRegion);
		
		mainPanel.setPosition(294, 0);
		mainPanelIA.setPosition(648, 0);
		topPanel.setPosition(410, VIRTUAL_HEIGHT-topPanelRegion.getRegionHeight());
		topPanelIA.setPosition(690, VIRTUAL_HEIGHT-topPanelRegion.getRegionHeight());
		leftPanel.setPosition(70, 400);
		leftPanelIA.setPosition(1040, 400);
	}
	
	@Override
	public void draw(SpriteBatch batch, float arg1) {
		mainPanel.draw(batch);
		mainPanelIA.draw(batch);
		topPanel.draw(batch);
		topPanelIA.draw(batch);
		leftPanel.draw(batch);
		leftPanelIA.draw(batch);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
