package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundActor extends Actor{
	
	private static final int VIRTUAL_WIDTH = 1280;
	private static final int VIRTUAL_HEIGHT = 768;
	
	float scrollTimer = 0.0f;
	
	Texture SkyTex;
    Sprite SkySprite;
    
	Texture HillsTex;

	public BackgroundActor() {
		SkyTex = new Texture(Gdx.files.internal("images/menu/sky.png"));
		SkyTex.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		SkySprite = new Sprite(SkyTex, 0,256,VIRTUAL_WIDTH, VIRTUAL_HEIGHT+256);
		SkySprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		HillsTex = new Texture(Gdx.files.internal("images/menu/hills.png"));
		
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
