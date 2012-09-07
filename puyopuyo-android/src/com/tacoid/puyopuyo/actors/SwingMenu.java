package com.tacoid.puyopuyo.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;

public class SwingMenu extends Group{
	private int VIRTUAL_WIDTH;
	
	private static final float BUTTON_HEIGHT = 250;
	
	private List<Actor> buttons;

	private Interpolation interpBush;
	private Interpolation interpButton;
	private float timeBush;
	private float timeButton;
	
	private class BushActor extends Actor {
		private Texture ForegroundTex;
		
		
		public BushActor() {
			ForegroundTex = new Texture(Gdx.files.internal("images/menu/foreground.png"));
		}
		
		@Override
		public void draw(SpriteBatch batch, float delta) {
		     timeBush+=Gdx.graphics.getDeltaTime()*0.4;
		     timeButton+=Gdx.graphics.getDeltaTime()*0.4;
		     for(int i=0; i<buttons.size(); i++) {
		    	 buttons.get(i).y = interpButton.apply(0, BUTTON_HEIGHT, Math.min(timeButton,1.0f));
		     }
			batch.draw(ForegroundTex,0,interpBush.apply(-ForegroundTex.getHeight(),0.0f,Math.min(timeBush,1.0f)));
		}

		@Override
		public Actor hit(float arg0, float arg1) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public SwingMenu(ScreenOrientation orientation) {
		
		if(orientation == ScreenOrientation.LANDSCAPE) {
			VIRTUAL_WIDTH = 1280;
		} else {
			VIRTUAL_WIDTH = 768;
		}
		
		interpBush = new Interpolation.Pow(2);
		interpButton = new Interpolation.SwingOut(1.5f);
		addActor(new BushActor());
	}
	
	public void initBegin() {
		buttons = new ArrayList<Actor>();
	}
	
	public void addButton( Actor actor) {
		buttons.add(actor);
	}
	
	public void initEnd() {
		for(int i=0; i<buttons.size(); i++) {
			/*buttons.get(i).x = VIRTUAL_WIDTH*(i+1)/(buttons.size()+1)-128;*/
			buttons.get(i).x = (i+1)*(VIRTUAL_WIDTH-buttons.size()*256)/(buttons.size()+1)+i*256;
			buttons.get(i).y = 0;
			addActor(buttons.get(i));
		}
		addActor(new BushActor());
	}
	public void show() {
		timeBush = 0.5f;
		timeButton = 0.0f;
		this.touchable =true;
		this.visible = true;
	}
	
	public void hide() {
		this.touchable =false;
		this.visible = false;
	}

}
