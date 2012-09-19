package com.tacoid.puyopuyo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.tacoid.puyopuyo.PuyoPuyo.ScreenOrientation;
import com.tacoid.puyopuyo.SoundPlayer.SoundType;
import com.tacoid.puyopuyo.actors.BackgroundActor;
import com.tacoid.puyopuyo.actors.ControlerActor;
import com.tacoid.puyopuyo.actors.GameOverActor;
import com.tacoid.puyopuyo.actors.GridActor;
import com.tacoid.puyopuyo.actors.LandscapePanelActor;
import com.tacoid.puyopuyo.actors.MusicButtonActor;
import com.tacoid.puyopuyo.actors.NextPieceActor;
import com.tacoid.puyopuyo.actors.PauseMenu;
import com.tacoid.puyopuyo.actors.ScoreActor;
import com.tacoid.puyopuyo.actors.GameOverActor.GameOverType;
import com.tacoid.puyopuyo.logic.GameLogic;
import com.tacoid.puyopuyo.logic.IA;
import com.tacoid.puyopuyo.logic.State;

public class GameVersusScreen implements GameScreen {
	private static final int VIRTUAL_WIDTH = 1280;
	private static final int VIRTUAL_HEIGHT = 768;
	private static GameVersusScreen instance = null;
	private Stage stage;
	private GameLogic gameLogic;
	private GameLogic gameLogicIA;
	private GridActor gridActor;
	private GridActor gridActorIA;

	private IA ia;
	private InputProcessor controller;
	private ControlerActor controllerActor;
	private PauseMenu pauseMenu;
	private PauseButton pauseButton;
	
	private GameOverActor gameOver;
	
	

	private void addButton(Button button, int x, int y) {
		stage.addActor(button);
		button.x = x;
		button.y = y;
	}
	
	public class PauseButton extends Button {

		public PauseButton(TextureRegion region) {
			super(region);
		}
		
		public boolean touchDown(float x, float y, int pointer) {
			SoundPlayer.getInstance().playSound(SoundType.TOUCH_MENU, 0.5f, true);
			pauseMenu.show();
			return true;
		}

	}


	private GameVersusScreen() {
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				false);
		gameLogic = new GameLogic();
		gameLogicIA = new GameLogic();
		gameLogic.setOpponent(gameLogicIA);
		gameLogicIA.setOpponent(gameLogic);
		controller = new Controller(gameLogic, stage);
		gameOver = null;

		gridActor = new GridActor(gameLogic, 296, 26, 54, 48);
		NextPieceActor nextPieceActor = new NextPieceActor(gameLogic, 95, 500, 48);
		ScoreActor scoreActor = new ScoreActor(gameLogic, 550, 738);

		gridActorIA = new GridActor(gameLogicIA, 648, 26, 54, 48);
		NextPieceActor nextPieceActorIA = new NextPieceActor(gameLogicIA, 1066,	500, 48);
		ScoreActor scoreActorIA = new ScoreActor(gameLogicIA, 830, 738);
		

		TextureRegion pauseRegion = new TextureRegion(PuyoPuyo.getInstance().manager.get("images/pause_button.png",Texture.class), 32, 32);

		stage.addActor(new BackgroundActor(ScreenOrientation.LANDSCAPE));
		stage.addActor(new LandscapePanelActor());
		
		stage.addActor(gridActor);
		stage.addActor(nextPieceActor);
		stage.addActor(scoreActor);
		stage.addActor(gridActorIA);
		stage.addActor(nextPieceActorIA);
		stage.addActor(scoreActorIA);

		controllerActor = new ControlerActor(ScreenOrientation.LANDSCAPE, gameLogic);
		stage.addActor(controllerActor);
		
		pauseButton = new PauseButton(pauseRegion);
		addButton(pauseButton,10,VIRTUAL_HEIGHT-10-pauseRegion.getRegionHeight());
		addButton(MusicButtonActor.createMusicButton(),VIRTUAL_WIDTH-42, VIRTUAL_HEIGHT-42);
		
		pauseMenu = new PauseMenu(this, ScreenOrientation.LANDSCAPE);
		stage.addActor(pauseMenu);

		ia = new IA(gameLogicIA);
	}

	public static GameVersusScreen getInstance() {
		if (instance == null) {
			instance = new GameVersusScreen();
		}
		return instance;
	}
	
	public static void freeInstance() {
		instance = null;
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
		GLCommon gl = Gdx.gl;

		if (gameLogic.getState() != State.LOST
				&& gameLogicIA.getState() != State.LOST) {
			// Update model
			gameLogic.update(delta);
			gameLogicIA.update(delta);
			ia.update(delta);
		} else {
			if(gameOver == null) {
				if(gameLogic.getState() == State.LOST){
					gameOver = new GameOverActor(GameOverType.LOSE, VIRTUAL_WIDTH/2, 2*VIRTUAL_HEIGHT/3);
				} else {
					gameOver = new GameOverActor(GameOverType.WIN, VIRTUAL_WIDTH/2, 2*VIRTUAL_HEIGHT/3);
					
				}
				
				stage.addActor(gameOver);
			}
		}

		// clear previous frame
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();

	}

	@Override
	public void resize(int arg0, int arg1) {
		stage.setViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		stage.getCamera().position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller );
	}

	@Override
	public void init() {
		gameLogic.init();
		gameLogicIA.init();
	}

	@Override
	public void gamePause() {
		gameLogic.pause();
		gameLogicIA.pause();
		controllerActor.touchable = false;
		pauseButton.touchable = false;
	}

	@Override
	public void gameResume() {
		gameLogic.resume();
		gameLogicIA.resume();
		controllerActor.touchable = true;
		pauseButton.touchable = true;
	}

}
