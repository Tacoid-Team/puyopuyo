package com.tacoid.puyopuyo.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tacoid.puyopuyo.PuyoPuyo;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;
import com.tacoid.puyopuyo.logic.GameLogic;
import com.tacoid.puyopuyo.logic.GameLogic.MoveType;

public class ControlerActor extends Group {
	
	public ControlerActor(ScreenOrientation orientation, GameLogic logic){
		
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
			addActor(new MoveButton(MoveType.LEFT, logic, 20, 260, leftRegion, leftDownRegion));
			addActor(new MoveButton(MoveType.RIGHT, logic, 145, 200, rightRegion, rightDownRegion));
			addActor(new MoveButton(MoveType.DOWN, logic, 80, 40, downRegion, downDownRegion));
			addActor(new MoveButton(MoveType.ROT_LEFT, logic, 1000, 200, rotleftRegion, rotleftDownRegion));
			addActor(new MoveButton(MoveType.ROT_RIGHT, logic, 1125, 260, rotrightRegion, rotrightDownRegion));
			
			break;
		case PORTRAIT:
			addActor(new MoveButton(MoveType.LEFT, logic, 20, 200, leftRegion, leftDownRegion));
			addActor(new MoveButton(MoveType.RIGHT, logic, 145, 140, rightRegion, rightDownRegion));
			addActor(new MoveButton(MoveType.DOWN, logic, 80, 20, downRegion, downDownRegion));
			addActor(new MoveButton(MoveType.ROT_LEFT, logic, 488, 100, rotleftRegion, rotleftDownRegion));
			addActor(new MoveButton(MoveType.ROT_RIGHT, logic, 613, 160, rotrightRegion, rotrightDownRegion));
			
			break;
		default:
			break;
		}
	}

}
