package com.tacoid.pweek.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.I18nManager;
import com.tacoid.pweek.PreferenceManager;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.I18nManager.Language;
import com.tacoid.pweek.PreferenceManager.Preference;
import com.tacoid.pweek.Pweek.ScreenOrientation;
import com.tacoid.pweek.actors.BackgroundActor;

public class LanguageScreen implements Screen {
	
	private static final int VIRTUAL_WIDTH = 1280;
	private static final int VIRTUAL_HEIGHT = 768;
	private static LanguageScreen instance = null;
	
	private Stage stage;
	private Pweek puyopuyo = null;
	
	private class LangButton extends Button {
		
		public LangButton(TextureRegion region, final I18nManager.Language lang) {
			super(new TextureRegionDrawable(region));
			addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					I18nManager.getInstance().setLanguage(lang);
					PreferenceManager.getInstance().setPreference(Preference.LANGUAGE,lang.toString());
					puyopuyo.loadLocalizedAssets();
					puyopuyo.setScreen(GameServicesScreen.getInstance());
				}
			});
		}		
	}
	
	public static LanguageScreen getInstance() {
		if(instance == null) {
			instance = new LanguageScreen();
		}
		return instance;
	}
	
	private LanguageScreen() {
		
		this.puyopuyo = Pweek.getInstance();
		TextureRegion enRegion = new TextureRegion( puyopuyo.manager.get("images/menu/flag-en.png", Texture.class));
		TextureRegion frRegion = new TextureRegion( puyopuyo.manager.get("images/menu/flag-fr.png", Texture.class));
		LangButton enButton = new LangButton(enRegion, Language.ENGLISH);
		LangButton frButton = new LangButton(frRegion, Language.FRENCH);
		
		this.puyopuyo = Pweek.getInstance();
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		
		/* Positionnement des bouttons aux premier tier et deuxieme tier de l'�cran */
		enButton.setX(2*VIRTUAL_WIDTH/3 - enRegion.getRegionWidth()/2);
		enButton.setY(VIRTUAL_HEIGHT/3 - enRegion.getRegionHeight()/2);
		frButton.setX(VIRTUAL_WIDTH/3 - frRegion.getRegionWidth()/2);
		frButton.setY(VIRTUAL_HEIGHT/3 - enRegion.getRegionHeight()/2);
		
		stage.addActor(new BackgroundActor(ScreenOrientation.LANDSCAPE));
		stage.addActor(enButton);
		stage.addActor(frButton);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		
		stage.act(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int arg0, int arg1) {
		stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		stage.getCamera().position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

}
