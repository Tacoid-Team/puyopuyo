package com.tacoid.puyopuyo.logic;

public class IA {
	float sum = 0f;
	private GameLogic logic;

	public IA(GameLogic logic) {
		this.logic = logic;
	}
	
	private class Solution {
		int potentiel;
		int m;
		
		public Solution(int potentiel, int m) {
			this.potentiel = potentiel;
			this.m = m;
		}
	}

	private Solution choice(int n, GameLogic cl, int m) {
		if (n == 0) {
			cl.poseEtGravity();
			cl.gridFF = new boolean[cl.LINES][cl.COLUMNS];
			int[][] grid = cl.getGrid();
			int potentiel = 0;
			for (int l = 0; l < cl.LINES; l++) {
				for (int c = 0; c < cl.COLUMNS; c++) {
					if (grid[l][c] > 0 && !cl.gridFF[l][c]) {
						int count = cl.floodfill(l, c, grid[l][c], null);
						if (count > 1) {
							potentiel += count * count;
						}
					}
				}
			}
			return new Solution(potentiel, m);
		} else {
			GameLogic[] logics = new GameLogic[5];

			logics[0] = new GameLogic(cl);
			logics[1] = new GameLogic(cl);
			logics[1].rotateLeft();
			logics[2] = new GameLogic(cl);
			logics[2].rotateRight();
			logics[3] = new GameLogic(cl);
			logics[3].moveLeft();
			logics[4] = new GameLogic(cl);
			logics[4].moveRight();

			Solution scoreMax = choice(0, logics[0], m);
			int max = 0;
			for (int i = 1; i < 5; i++) {
				Solution score = choice(n - 1, logics[i], m + 1);
				if (score.potentiel > scoreMax.potentiel || (score.potentiel == scoreMax.potentiel && score.m < scoreMax.m)) {
					max = i;
					scoreMax = score;
				}
			}

			if (n == 5) {
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

			return scoreMax;
		}
	}

	public void update(float delta) {
		if (logic.getState() == State.MOVE) {
			sum += delta;
		}
		if (sum > 0.2) {
			sum = 0;

			choice(5, logic, 0);
		}
	}
}
