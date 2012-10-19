package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;

public class BackgroundActor extends Actor{
	
	private static final int VIRTUAL_WIDTH = 1280;
	private static final int VIRTUAL_HEIGHT = 768;
	
	float scrollTimer = 0.0f;
	
	Texture SkyTex;
    Sprite SkySprite;
    
	Texture HillsTex;

	public BackgroundActor(ScreenOrientation orientation) {
		switch(orientation) {
		case LANDSCAPE:
			SkyTex = PuyoPuyo.getInstance().manager.get("images/menu/sky.png", Texture.class);
			SkyTex.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			SkySprite = new Sprite(SkyTex, 0,256,VIRTUAL_WIDTH, VIRTUAL_HEIGHT+256);
			SkySprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
			break;
		case PORTRAIT:
			SkyTex = PuyoPuyo.getInstance().manager.get("images/menu/sky-portrait.png", Texture.class);
			SkyTex.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			SkySprite = new Sprite(SkyTex, 0,0,VIRTUAL_HEIGHT, VIRTUAL_WIDTH);
			SkySprite.setSize(VIRTUAL_HEIGHT, VIRTUAL_WIDTH);
		default:
			break;
		}
		HillsTex = PuyoPuyo.getInstance().manager.get("images/menu/hills.png", Texture.class);
	}
	
	@Override
	public void draw(SpriteBatch batch, float delta) {
		scrollTimer+=Gdx.graphics.getDeltaTime()*0.01;
		if(scrollTimer>1.0f)
			scrollTimer = 0.0f;
		SkySprite.setU(scrollTimer);
		SkySprite.setU2(scrollTimer+1);
		SkySprite.draw(batch);
		batch.draw(HillsTex,0,0);
	}

	@Override
	public Actor hit(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
