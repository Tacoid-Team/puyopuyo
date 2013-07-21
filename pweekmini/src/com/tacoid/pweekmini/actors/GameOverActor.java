package com.tacoid.pweekmini.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.I18nManager;
import com.tacoid.pweek.ScoreManager;
import com.tacoid.pweek.SoundPlayer;
import com.tacoid.pweek.ScoreManager.GameType;
import com.tacoid.pweek.SoundPlayer.SoundType;
import com.tacoid.pweek.screens.GameScreen;
import com.tacoid.pweekmini.screens.MainMenuScreen;
import com.tacoid.pweekmini.PweekMini;
import com.tacoid.pweekmini.SwingMenu;
import com.tacoid.tracking.TrackingManager;

public class GameOverActor extends Group {

	static public enum GameOverType {
		WIN,
		LOSE,
		GAMEOVER
	};

	private class QuitButton extends Button {

		public QuitButton(TextureRegion regionUp, TextureRegion regionDown) {
			super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown));

			addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					PweekMini.getInstance().setScreen(MainMenuScreen.getInstance());
				}

			});
		}		
	}

	private class ReplayButton extends Button {

		public ReplayButton(TextureRegion regionUp, TextureRegion regionDown) {
			super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown));

			addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					gameScreen.init();
					PweekMini.getInstance().getHandler().showAds(false);
					hide();
				}
			});
		}
	}

	private Sprite gameOverSprite;
	private GameScreen gameScreen;
	private SwingMenu menu;
	private float x, y;

	private int highScore = 0;
	private boolean newHighScore = false;

	private BitmapFont font;

	private I18nManager i18n;
	private NinePatch scorePanelNinePatch;

	public GameOverActor(TextureAtlas atlasPlank, TextureAtlas atlasPanels, TextureAtlas atlasButtons, BitmapFont font, GameScreen gs, float x, float y) {
		this.x = x;
		this.y = y;
		gameScreen = gs;

		i18n = I18nManager.getInstance();

		TextureRegion quitterRegion = atlasPlank.findRegion("quitter");
		TextureRegion rejouerRegion = atlasPlank.findRegion("rejouer");
		gameOverSprite = new Sprite(atlasPlank.findRegion("gameover"));
		scorePanelNinePatch = new NinePatch(atlasButtons.findRegion("frame-ninepatch"), 20, 20, 20, 20);

		menu = new SwingMenu();
		menu.initBegin("gameover-simple");
		menu.addButton(new ReplayButton(rejouerRegion, rejouerRegion));
		menu.addButton(new QuitButton(quitterRegion, quitterRegion));
		menu.initEnd();

		this.addActor(menu);

		gameOverSprite.setPosition(x-gameOverSprite.getWidth()/2, y);

		this.font = font;
		font.setScale(1f);

		this.hide();
		this.setVisible(true);
	}

	public void show(GameOverType type) {
		menu.show("gameover-simple");

		this.setVisible(true);
		this.highScore = ScoreManager.getInstance().getScore(gameScreen.getGameType());
		this.newHighScore = false;
		if(highScore < gameScreen.getScore() && gameScreen.getGameType() != GameType.VERSUS_IA) {
			this.newHighScore = true;
		}
		ScoreManager.getInstance().setScore(gameScreen.getGameType(), gameScreen.getScore());

		PweekMini.getInstance().getHandler().showAds(true);

		/* TRACKING */
		TrackingManager.getTracker().trackEvent("gameplay", "game_over", gameScreen.getGameType().toString()+"_"+type.toString(), (long) gameScreen.getElapsedTime());
	}

	public void hide() {
		menu.hideInstant();
		this.setVisible(false);
	}

	public void draw(SpriteBatch batch, float arg1) {
		String message;
		super.draw(batch,arg1);

		font.setScale(1f);
		font.setColor(1f, 1f, 1f, 1f);
		message =  i18n.getString("score") + String.valueOf(gameScreen.getScore());
		
		scorePanelNinePatch.draw(batch, x - 125, y - (40 + 2 * font.getBounds(message).height), 250, font.getBounds(message).height * 2 + 40);
		
		font.draw(batch, message, x - font.getBounds(message).width / 2, y - 15);
		if (newHighScore) {
			message = i18n.getString("nouveau_record");
			font.draw(batch, i18n.getString("nouveau_record"), x - font.getBounds(message).width/2, y - font.getBounds(message).height - 20);
		} else {
			message = i18n.getString("record") + String.valueOf(highScore);
			font.draw(batch, i18n.getString("record") + String.valueOf(highScore), x - font.getBounds(message).width/2, y - font.getBounds(message).height - 20);
		}

		gameOverSprite.draw(batch);

	}
}