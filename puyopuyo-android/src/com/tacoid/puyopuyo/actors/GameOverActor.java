package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.tacoid.puyopuyo.GameScreen;
import com.tacoid.puyopuyo.MainMenuScreen;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.SoundPlayer;
import com.tacoid.puyopuyo.SoundPlayer.SoundType;

public class GameOverActor extends Group {
	
	static public enum GameOverType {
		WIN,
		LOSE,
		GAMEOVER
	};
	
	private class QuitButton extends Button {

		public QuitButton(TextureRegion regionUp, TextureRegion regionDown) {
			super(regionUp, regionDown);
			// TODO Auto-generated constructor stub
		}
		
		public boolean touchDown(float x, float y, int pointer) {
			SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 1.0f, true);
			return true;
		}
		
		public void touchUp(float x, float y, int pointer) {
			PuyoPuyo.getInstance().setScreen(MainMenuScreen.getInstance());
		}
		
	}
	
	private class ReplayButton extends Button {

		public ReplayButton(TextureRegion regionUp, TextureRegion regionDown) {
			super(regionUp, regionDown);
			// TODO Auto-generated constructor stub
		}
		
		public boolean touchDown(float x, float y, int pointer) {
			SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 1.0f, true);
			return true;
		}
		
		public void touchUp(float x, float y, int pointer) {
			gameScreen.init();
			hide();
		}
		
	}
	
	private Sprite winSprite;
	private Sprite loseSprite;
	private GameScreen gameScreen;
	private SwingMenu menu;
	private GameOverType type;
	
	public GameOverActor(GameScreen gs, float x, float y) {
		
		
		TextureRegion quitterRegion = PuyoPuyo.getInstance().atlasPlank.findRegion("quitter-fr");
		TextureRegion rejouerRegion = PuyoPuyo.getInstance().atlasPlank.findRegion("rejouer-fr");
		winSprite = new Sprite(PuyoPuyo.getInstance().atlasMessages.findRegion("gagne-fr"));
		loseSprite = new Sprite(PuyoPuyo.getInstance().atlasMessages.findRegion("perdu-fr"));
		
		menu = new SwingMenu(gs.getOrientation());
		menu.initBegin();
		menu.addButton(new ReplayButton(rejouerRegion, rejouerRegion));
		menu.addButton(new QuitButton(quitterRegion, quitterRegion));
		menu.initEnd();
		
		this.addActor(menu);
		
		winSprite.setPosition(x-winSprite.getWidth()/2, y-winSprite.getHeight()/2);
		loseSprite.setPosition(x-loseSprite.getWidth()/2, y-loseSprite.getHeight()/2);
		
		gameScreen = gs;
		this.type = GameOverType.GAMEOVER;
		
		this.hide();

	}
	
	public void show(GameOverType type) {
		menu.show();
		this.type = type;
		this.visible = true;
	}
	
	public void hide() {
		menu.hide();
		this.visible = false;
	}
	
	public void draw(SpriteBatch batch, float arg1) {
		super.draw(batch,arg1);
		switch(type) {
		case GAMEOVER:
			winSprite.draw(batch);
			break;
		case LOSE:
			loseSprite.draw(batch);
			break;
		case WIN:
			winSprite.draw(batch);
			break;
		}
	}
}
