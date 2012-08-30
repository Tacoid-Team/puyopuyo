package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;
import com.tacoid.puyopuyo.logic.GameLogic;
import com.tacoid.puyopuyo.logic.GameLogic.MoveType;

public class ControlerActor extends Group {
	
	public enum ControlerLayout {
		CLASSIC,
		OLD_SCHOOL
	};
	
	private ControlerLayout layout;
	private ScreenOrientation orientation;
	private GameLogic logic;
	
	public ControlerActor(ControlerLayout layout, ScreenOrientation orientation, GameLogic logic){
		this.layout = layout;
		this.orientation = orientation;
		this.logic = logic;
		
		/* Chargement textures des boutons */
		TextureRegion leftRegion = PuyoPuyo.getInstance().atlasControls.findRegion("left");
		TextureRegion leftDownRegion =  PuyoPuyo.getInstance().atlasControls.findRegion("left_down");
		TextureRegion rightRegion =  PuyoPuyo.getInstance().atlasControls.findRegion("right");
		TextureRegion rightDownRegion =  PuyoPuyo.getInstance().atlasControls.findRegion("right_down");
		TextureRegion rotleftRegion =  PuyoPuyo.getInstance().atlasControls.findRegion("rotleft");
		TextureRegion rotleftDownRegion = PuyoPuyo.getInstance().atlasControls.findRegion("rotleft_down");
		TextureRegion rotrightRegion = PuyoPuyo.getInstance().atlasControls.findRegion("rotright");
		TextureRegion rotrightDownRegion = PuyoPuyo.getInstance().atlasControls.findRegion("rotright_down");
		TextureRegion downRegion = PuyoPuyo.getInstance().atlasControls.findRegion("down");
		TextureRegion downDownRegion = PuyoPuyo.getInstance().atlasControls.findRegion("down_down");
		
		switch(orientation) {
		case LANDSCAPE:
			addActor(new MoveButton(MoveType.LEFT, logic, 20, 290, leftRegion, leftDownRegion));
			addActor(new MoveButton(MoveType.RIGHT, logic, 1120, 290, rightRegion, rightDownRegion));
			addActor(new MoveButton(MoveType.ROT_LEFT, logic, 20, 150, rotleftRegion, rotleftDownRegion));
			addActor(new MoveButton(MoveType.ROT_RIGHT, logic, 1120, 150, rotrightRegion, rotrightDownRegion));
			addActor(new MoveButton(MoveType.DOWN, logic, 20, 10, downRegion, downDownRegion));
			break;
		case PORTRAIT:
			addActor(new MoveButton(MoveType.LEFT, logic, 29, 10, leftRegion, leftDownRegion));
			addActor(new MoveButton(MoveType.RIGHT, logic, 570, 10, rightRegion, rightDownRegion));
			addActor(new MoveButton(MoveType.ROT_LEFT, logic, 29, 162, rotleftRegion, rotleftDownRegion));
			addActor(new MoveButton(MoveType.ROT_RIGHT, logic, 570, 162, rotrightRegion, rotrightDownRegion));
			addActor(new MoveButton(MoveType.DOWN, logic, 200, 5, downRegion, downDownRegion));
			break;
		default:
			break;
		}
	}

}
