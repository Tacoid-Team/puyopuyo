package com.tacoid.utils.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class Menu implements Screen {
	private static final int VIRTUAL_WIDTH = 1024;
	private static final int VIRTUAL_HEIGHT = 576;
	
	protected Stage stage;
	
	public Menu() {
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
	}
	
	public void addWidget(MenuWidget widget, float x, float y) {
		widget.setX(x);
		widget.setY(y);
		stage.addActor(widget);
		
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
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int arg0, int arg1) {
		stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		stage.getCamera().position.set(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, 0);

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
