package com.tacoid.pweekmini;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class SwingMenu extends Group{
	private int VIRTUAL_WIDTH = 480;
	
	private final double BUSH_HIDE_SPEED = 0.6;
	private final double BUTTON_HIDE_SPEED = 1.0;
	private final double BUSH_SHOW_SPEED = 0.8;
	private final double BUTTON_SHOW_SPEED = 0.9;
	
	private static final float BUTTON_HEIGHT = 250;
	
	private enum State {
		SHOWING,
		HIDING,
		IDLE;
	};
	
	private Map<String,Group> menus;
	private Group currentGroup;
	private String currentName;

	private Interpolation interpButton;
	private float timeBush;
	private float timeButton;
	private State state = State.IDLE;
	
	private boolean switching = false;
	private String nextMenu;
	
	private class BushActor extends Actor {
		public BushActor() {
		}
		
		@Override
		public void draw(SpriteBatch batch, float delta) {
			boolean keepGoing = false;
			switch(state) {
			case HIDING:
				if(timeBush >= 0.5f) {
					timeBush-=Gdx.graphics.getDeltaTime()*BUSH_HIDE_SPEED;
					keepGoing = true;
				}
				
				if( timeButton >= -0.1f) {
					timeButton-=Gdx.graphics.getDeltaTime()*BUTTON_HIDE_SPEED;
					keepGoing = true;
				}
				
				if(!keepGoing) {
					if(switching) {
						show(nextMenu);
					} else {
						setTouchable(Touchable.disabled);
						setVisible(false);
						state = State.IDLE;
					}
				}
				break;
			case SHOWING:
				if(timeBush <= 1.0f) {
					timeBush+=Gdx.graphics.getDeltaTime()*BUSH_SHOW_SPEED;
					keepGoing = true;
				}
				
				if( timeButton <= 1.0f) {
					timeButton+=Gdx.graphics.getDeltaTime()*BUTTON_SHOW_SPEED;
					keepGoing = true;
				} 
				if(!keepGoing){
					state = State.IDLE;
				}
				break;
			case IDLE:
				break;
			}
			
			currentGroup.setY(interpButton.apply(-BUTTON_HEIGHT, 50, Math.min(timeButton,1.0f)));
			/*
			for(int i=0; i<currentGroup.size(); i++) {
				currentList.get(i).y = interpButton.apply(0, BUTTON_HEIGHT, Math.min(timeButton,1.0f));
			}*/
		}

		@Override
		public Actor hit(float arg0, float arg1, boolean touchable) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public SwingMenu() {
		interpButton = new Interpolation.SwingOut(1.5f);
		menus = new HashMap<String, Group>();
	}
	
	public void initBegin(String menu) {
		menus.put(menu, new Group());
		currentGroup = menus.get(menu);
		currentName = menu;
	}
	
	public void addButton( Actor actor) {
		currentGroup.addActor(actor);
	}
	
	public void initEnd() {
		int size = currentGroup.getChildren().size;
		int i = 0;
		for (Actor a : currentGroup.getChildren()) {
			a.setX(VIRTUAL_WIDTH / 2 - a.getWidth() / 2);
			a.setY((size - (i + 1)) * (a.getHeight() + 20));
			i++;
		}
	}
	public void show(String menu) 
	{
		this.clear();
		currentGroup = menus.get(menu);
		currentName = menu;
		
		this.addActor(currentGroup);
		this.addActor(new BushActor());
		
		state=State.SHOWING;
		timeBush = 0.5f;
		timeButton = -0.2f;
		setTouchable(Touchable.enabled);
		setVisible(true);
		
		/* On initialise le y ici, car dans la file de rendering, les bouttons sont en premiers, et passent donc apr�s le BushActor qui set la position des bouttons. */
		currentGroup.setY(interpButton.apply(-BUTTON_HEIGHT, 0, Math.min(timeButton,1.0f)));
		
		switching = false;
	}
	
	public void hide() {
		state=State.HIDING;
	}
	
	public void hideInstant() {
		state=State.HIDING;
		timeBush = 0.5f;
		timeButton = -0.2f;
	}
	
	/* Change de menu avec animation */
	public void switchMenuAnimated(String menu) {
		this.hide();
		switching = true;
		nextMenu = menu;
	}
	
	/* Change de menu sans animation */
	public void switchMenu(String menu) {
		this.clear();
		currentGroup = menus.get(menu);
		
		this.addActor(currentGroup);
		this.addActor(new BushActor());
	}
	
	public String getCurrentMenu() {
		return currentName;
	}
	

}
