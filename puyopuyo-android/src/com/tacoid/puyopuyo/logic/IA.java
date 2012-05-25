package com.tacoid.puyopuyo.logic;

public class IA {
	float sum = 0f;
	private GameLogic logic;

	public IA(GameLogic logic) {
		this.logic = logic;
	}
	
	private void choice() {
		GameLogic[] logics = new GameLogic[5];
		
		logics[0] = new GameLogic(logic);
		logics[1] = new GameLogic(logic);
		logics[1].rotateLeft();
		logics[2] = new GameLogic(logic);
		logics[2].rotateRight();
		logics[3] = new GameLogic(logic);
		logics[3].moveLeft();
		logics[4] = new GameLogic(logic);
		logics[4].moveRight();
		
		int max = 0;
		int scoreMax = 0;
		for (int i = 0; i < 5; i++) {
			GameLogic l = logics[i];
			l.descendreEtPose();
			if (scoreMax < l.getScore()) {
				scoreMax = l.getScore();
				max = i;
			}
		}
		
		switch (max) {
		case 1:
			logic.rotateLeft();
			break;
		case 2:
			logic.rotateRight();
			break;
		case 3:
			logic.moveLeft();
			break;
		case 4:
			logic.moveRight();
			break;
		}
	}
	
	public void update(float delta) {
		if (logic.getState() == State.MOVE) {
			sum += delta;
		}
		if (sum > 0.2) {
			sum = 0;
			
			System.out.println("choice");
			choice();
		}
	}
}
