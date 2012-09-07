package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.tacoid.puyopuyo.GameVersusScreen;
import com.tacoid.puyopuyo.MainMenuScreen;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.SoundPlayer;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;
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
			GameVersusScreen.freeInstance();
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
		}
		
	}
	
	private Sprite sprite;
	
	public GameOverActor(GameOverType type, float x, float y) {
		
		SwingMenu menu = new SwingMenu(ScreenOrientation.LANDSCAPE);
		TextureRegion quitterRegion = PuyoPuyo.getInstance().atlasPlank.findRegion("quitter-fr");
		TextureRegion rejouerRegion = PuyoPuyo.getInstance().atlasPlank.findRegion("rejouer-fr");
		menu.initBegin();
		menu.addButton(new ReplayButton(rejouerRegion, rejouerRegion));
		menu.addButton(new QuitButton(quitterRegion, quitterRegion));
		menu.initEnd();
		menu.show();
		this.addActor(menu);
		
		switch(type) {
		case WIN:
			sprite = new Sprite(PuyoPuyo.getInstance().manager.get("images/gagne-fr.png", Texture.class));
			break;
		case LOSE:
			sprite = new Sprite(PuyoPuyo.getInstance().manager.get("images/perdu-fr.png", Texture.class));
			break;
		default:
			sprite = new Sprite(PuyoPuyo.getInstance().manager.get("images/perdu-fr.png", Texture.class));
		}
		sprite.setPosition(x-sprite.getWidth()/2, y-sprite.getHeight()/2);

	}
	
	public void draw(SpriteBatch batch, float arg1) {
		super.draw(batch,arg1);
		sprite.draw(batch);
	}
	

	
	
}
