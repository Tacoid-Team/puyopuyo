package com.tacoid.pweek.actors;

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
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.ScoreManager;
import com.tacoid.pweek.SoundPlayer;
import com.tacoid.pweek.Pweek.ScreenOrientation;
import com.tacoid.pweek.ScoreManager.GameType;
import com.tacoid.pweek.SoundPlayer.SoundType;
import com.tacoid.pweek.screens.GameScreen;
import com.tacoid.pweek.screens.GameVersusScreen;
import com.tacoid.pweek.screens.MainMenuScreen;
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
					newUnlock = false;
					Pweek.getInstance().setScreen(MainMenuScreen.getInstance());
				}
		
			});
		}		
	}
	
	private class ShareButton extends Button {

		public ShareButton(TextureRegion regionUp, TextureRegion regionDown) {
			super(new TextureRegionDrawable(regionUp), new TextureRegionDrawable(regionDown));
			
			addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					System.out.println("Share");
					return true;
				}
				
				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					Pweek.getInstance().shareLauncher.share("Try Pweek!","Hello world #Pweek");
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
					newUnlock = false;
					Pweek.getInstance().getHandler().showAds(false);
					hide();
				}
			});
		}
	}
	
	private class NextLevelButton extends Button {

		public NextLevelButton(TextureRegion regionUp, TextureRegion regionDown) {
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
					((GameVersusScreen)gameScreen).setLevel(gameScreen.getLevel()+1);
					gameScreen.init();
					newUnlock = false;
					Pweek.getInstance().getHandler().showAds(false);
					hide();
				}
			});
		}
	}
	
	private Sprite winSprite;
	private Sprite loseSprite;
	private Sprite gameOverSprite;
	private GameScreen gameScreen;
	private SwingMenu menu;
	private GameOverType type;
	
	private int highScore = 0;
	private boolean newHighScore = false;
	private boolean newUnlock = false;
	
	private BitmapFont font;
	
	private I18nManager i18n;
	private NinePatch scorePanelNinePatch;
	
	public GameOverActor(TextureAtlas atlasPlank, TextureAtlas atlasPanels, TextureAtlas atlasButtons, BitmapFont font, GameScreen gs, float x, float y) {
		gameScreen = gs;

		i18n = I18nManager.getInstance();
		
		TextureRegion nextRegion = null;
		TextureRegion quitterRegion = atlasPlank.findRegion("quitter");
		TextureRegion rejouerRegion = atlasPlank.findRegion("rejouer");
		TextureRegion shareRegion = atlasPlank.findRegion("share");
		
		Button shareButton = new ShareButton(shareRegion, shareRegion);
		
		
		
		if (gameScreen.getGameType() == GameType.VERSUS_IA) {
			nextRegion = atlasPlank.findRegion("suivant");
			winSprite = new Sprite(atlasPlank.findRegion("gagne"));
			loseSprite = new Sprite(atlasPlank.findRegion("perdu"));
		}
		gameOverSprite = new Sprite(atlasPlank.findRegion("gameover"));
		
		scorePanelNinePatch = new NinePatch(atlasButtons.findRegion("frame-ninepatch"), 20, 20, 20, 20);
		
		menu = new SwingMenu(gs.getOrientation());
		menu.initBegin("gameover-simple");
		menu.addButton(new ReplayButton(rejouerRegion, rejouerRegion));
		menu.addButton(new QuitButton(quitterRegion, quitterRegion));
		menu.initEnd();
		
		if (gameScreen.getGameType() == GameType.VERSUS_IA) {
			menu.initBegin("gameover-next");
			menu.addButton(new NextLevelButton(nextRegion, nextRegion));
			menu.addButton(new ReplayButton(rejouerRegion, rejouerRegion));
			menu.addButton(new QuitButton(quitterRegion, quitterRegion));
			menu.initEnd();
		}
		
		this.addActor(menu);
		
		if (gameScreen.getGameType() == GameType.VERSUS_IA) {
			winSprite.setPosition(x-winSprite.getWidth()/2, y-winSprite.getHeight()/2);
			loseSprite.setPosition(x-loseSprite.getWidth()/2, y-loseSprite.getHeight()/2);
		}
		gameOverSprite.setPosition(x-gameOverSprite.getWidth()/2, y-gameOverSprite.getHeight()/2);
		
		this.type = GameOverType.GAMEOVER;
		this.addActor(shareButton);
		this.font = font;
		font.setScale(0.8f);
		
		this.hide();
		this.setVisible(true);
	}
	
	public void show(GameOverType type) {
		
		boolean hasNext = false;
		
		if(gameScreen.getGameType() == GameType.VERSUS_IA && type == GameOverType.WIN) {
			if(gameScreen.getLevel()<3 && !ScoreManager.getInstance().isLevelUnlocked(GameType.VERSUS_IA, gameScreen.getLevel()+1)) {
				ScoreManager.getInstance().unlockLevel(GameType.VERSUS_IA, gameScreen.getLevel()+1);
				newUnlock = true;
			} 
			if(gameScreen.getLevel()<3) {
				hasNext = true;
			}
		}
		
		if(hasNext) {
			menu.show("gameover-next");
		} else {
			menu.show("gameover-simple");
		}
		
		this.type = type;
		this.setVisible(true);
		this.highScore = ScoreManager.getInstance().getScore(gameScreen.getGameType());
		this.newHighScore = false;
		if(highScore < gameScreen.getScore() && gameScreen.getGameType() != GameType.VERSUS_IA) {
			ScoreManager.getInstance().setScore(gameScreen.getGameType(), gameScreen.getScore());
			this.newHighScore = true;
		}

		Pweek.getInstance().getHandler().showAds(true);
		
		/* TRACKING */
		if(gameScreen.getGameType() == GameType.VERSUS_IA) {
			TrackingManager.getTracker().trackEvent("gameplay", "game_over", gameScreen.getGameType().toString()+"_"+gameScreen.getLevel()+"_"+type.toString(), (long) gameScreen.getElapsedTime());
		} else {
			TrackingManager.getTracker().trackEvent("gameplay", "game_over", gameScreen.getGameType().toString()+"_"+type.toString(), (long) gameScreen.getElapsedTime());
		}
	}
	
	public void hide() {
		menu.hideInstant();
		this.setVisible(false);
	}
	
	public void draw(SpriteBatch batch, float arg1) {
		float x=0,y=0;
		String message;
		super.draw(batch,arg1);
		if(gameScreen.getOrientation() == ScreenOrientation.LANDSCAPE) {
			x = 640;
			y = 500;
		} else {
			x = 384;
			y = 558;
		}
		if (gameScreen.getGameType() != GameType.VERSUS_IA) {
			font.setScale(0.8f);
			font.setColor(1f, 1f, 1f, 1f);
			message =  i18n.getString("score") + String.valueOf(gameScreen.getScore());
			
			scorePanelNinePatch.draw(batch, x - 150, y - (40 + 2 * font.getBounds(message).height), 300, font.getBounds(message).height * 2 + 60);
			
			font.draw(batch, message, x-font.getBounds(message).width/2,y);
			if(newHighScore) {
				message = i18n.getString("nouveau_record");
				font.draw(batch, i18n.getString("nouveau_record"), x-font.getBounds(message).width/2, y - 30f);
			}
			else {
				message = i18n.getString("record") + String.valueOf(highScore);
				font.draw(batch, i18n.getString("record") + String.valueOf(highScore), x-font.getBounds(message).width/2,y-30f);
			}
		} else if(newUnlock) {
			String levelname;
			font.setScale(0.8f);
			font.setColor(1f, 1f, 1f, 1f);
			switch(gameScreen.getLevel()+1) {
			case 1:
				levelname=i18n.getString("moyen");
				break;
			case 2:
				levelname=i18n.getString("difficile");
				break;
			case 3:
				levelname=i18n.getString("tres_difficile");
				break;
			default:
				levelname="Invalid level";
			}
			message = i18n.getString("niveau") + " " + levelname + " " + i18n.getString("deverrouille");
			scorePanelNinePatch.draw(batch, x-font.getBounds(message).width/2 - 40, y - (20 + font.getBounds(message).height), font.getBounds(message).width + 80, font.getBounds(message).height + 40);
			font.draw(batch, message, x-font.getBounds(message).width/2 ,y);
		}

		switch(type) {
		case GAMEOVER:
			gameOverSprite.draw(batch);
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