package com.tacoid.utils.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tacoid.puyopuyo.PuyoPuyo;

public abstract class MenuButton extends MenuWidget {

	private TextureRegion region;

	protected MenuButton(String image, int width, int height) {
		PuyoPuyo puyopuyo = PuyoPuyo.getInstance();
		Texture texture = puyopuyo.manager.get(image, Texture.class);
		region = new TextureRegion(texture, width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		batch.draw(region, x, y);
	}

	@Override
	public Actor hit(float x, float y) {
		return x >= -70 && x <= getWidth() && y >= 0 && y <= getHeight() ? this : null;
	}	
}