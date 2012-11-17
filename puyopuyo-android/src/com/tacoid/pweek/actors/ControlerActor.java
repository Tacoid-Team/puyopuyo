package com.tacoid.pweek.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tacoid.pweek.Pweek;
import com.tacoid.pweek.Pweek.ScreenOrientation;
import com.tacoid.pweek.logic.GameLogic;
import com.tacoid.pweek.logic.GameLogic.MoveType;

public class ControlerActor extends Group {
	
	public ControlerActor(ScreenOrientation orientation, GameLogic logic){
		
		/* Chargement textures des boutons */
		TextureRegion leftRegion = Pweek.getInstance().atlasControls.findRegion("left");
		TextureRegion leftDownRegion =  Pweek.getInstance().atlasControls.findRegion("left_down");
		TextureRegion rightRegion =  Pweek.getInstance().atlasControls.findRegion("right");
		TextureRegion rightDownRegion =  Pweek.getInstance().atlasControls.findRegion("right_down");
		TextureRegion rotleftRegion =  Pweek.getInstance().atlasControls.findRegion("rotleft");
		TextureRegion rotleftDownRegion = Pweek.getInstance().atlasControls.findRegion("rotleft_down");
		TextureRegion rotrightRegion = Pweek.getInstance().atlasControls.findRegion("rotright");
		TextureRegion rotrightDownRegion = Pweek.getInstance().atlasControls.findRegion("rotright_down");
		TextureRegion downRegion = Pweek.getInstance().atlasControls.findRegion("down");
		TextureRegion downDownRegion = Pweek.getInstance().atlasControls.findRegion("down_down");
		
		switch(orientation) {
		case LANDSCAPE:
			addActor(new MoveButton(MoveType.LEFT, logic, 20, 260, leftRegion, leftDownRegion));
			addActor(new MoveButton(MoveType.RIGHT, logic, 145, 200, rightRegion, rightDownRegion));
			addActor(new MoveButton(MoveType.DOWN, logic, 80, 50, downRegion, downDownRegion));
			addActor(new MoveButton(MoveType.ROT_LEFT, logic, 1000, 200, rotleftRegion, rotleftDownRegion));
			addActor(new MoveButton(MoveType.ROT_RIGHT, logic, 1125, 260, rotrightRegion, rotrightDownRegion));
			break;
		case PORTRAIT:
			addActor(new MoveButton(MoveType.LEFT, logic, 15, 300, leftRegion, leftDownRegion));
			addActor(new MoveButton(MoveType.RIGHT, logic, 140, 240, rightRegion, rightDownRegion));
			addActor(new MoveButton(MoveType.DOWN, logic, 75, 100, downRegion, downDownRegion));
			addActor(new MoveButton(MoveType.ROT_LEFT, logic, 488, 100, rotleftRegion, rotleftDownRegion));
			addActor(new MoveButton(MoveType.ROT_RIGHT, logic, 613, 160, rotrightRegion, rotrightDownRegion));
			break;
		default:
			break;
		}
	}

}
