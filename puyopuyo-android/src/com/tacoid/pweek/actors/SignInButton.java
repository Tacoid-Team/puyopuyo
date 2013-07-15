package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.tacoid.pweek.IGameService;
import com.tacoid.pweek.PreferenceManager;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.PreferenceManager.Preference;

public class SignInButton extends Button {

	private SignInButton(Drawable up, Drawable down) {
		super(up, down);
		
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				PreferenceManager.getInstance().setPreference(Preference.SIGNIN_GP, String.valueOf(true));
				Pweek.getInstance().getGameService().login();
			}
		});
	}
	
	public static SignInButton create(IGameService gs, TextureAtlas atlasGoogle, boolean large, boolean icon) {
		SignInButton instance;
		if (icon) {
			instance = new SignInButton(new TextureRegionDrawable(new TextureRegion(atlasGoogle.findRegion("White-signin_Small_base_32dp"))),
					new TextureRegionDrawable(new TextureRegion(atlasGoogle.findRegion("White-signin_Small_press_32dp"))));
		} else if (large) {
			instance = new SignInButton(new TextureRegionDrawable(new TextureRegion(atlasGoogle.findRegion("Red-signin_Medium_base_44dp"))),
					new TextureRegionDrawable(new TextureRegion(atlasGoogle.findRegion("Red-signin_Medium_press_44dp"))));
		} else {
			instance = new SignInButton(new TextureRegionDrawable(new TextureRegion(atlasGoogle.findRegion("White-signin_Medium_base_32dp"))),
		new TextureRegionDrawable(new TextureRegion(atlasGoogle.findRegion("White-signin_Medium_press_32dp"))));			
		} 
		return instance;
	}
}