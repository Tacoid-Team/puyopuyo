package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.PuyoPuyo;

public class LandscapePanelActor extends Actor {

	private Sprite mainPanel;
	private Sprite topPanel;
	private Sprite leftPanel;
	private Sprite mainPanelIA;
	private Sprite topPanelIA;
	private Sprite leftPanelIA;
	
	public LandscapePanelActor() {
		TextureRegion mainPanelRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/main-panel.png",Texture.class));
		TextureRegion leftPanelRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/left-panel.png",Texture.class));
		TextureRegion topPanelRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/top-panel.png",Texture.class));

		mainPanel = new Sprite(mainPanelRegion);
		mainPanelIA = new Sprite(mainPanelRegion);
		topPanel = new Sprite(topPanelRegion);
		topPanelIA = new Sprite(topPanelRegion);
		leftPanel = new Sprite(leftPanelRegion);
		leftPanelIA = new Sprite(leftPanelRegion);
		
		mainPanel.setPosition(214, -340);
		mainPanelIA.setPosition(728, -340);
		topPanel.setPosition(330, 640);
		topPanelIA.setPosition(730, 640);
		leftPanel.setPosition(20, 170);
		leftPanelIA.setPosition(1080, 170);
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
