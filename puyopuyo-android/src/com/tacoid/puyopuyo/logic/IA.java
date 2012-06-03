package com.tacoid.puyopuyo.logic;

public class IA {
	float sum = 0f;
	private GameLogic logic;
	private int potentiel;

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
			int diff = 0;
			cl.poseEtGravity();

			for (Coord c : cl.getPiece()) {
				int count = floodfill(c.l, c.c, c.coul, cl);
				if (count > 2) {
					diff = -(count - 1) * (count - 1) + count * count;
				} else if (count == 2) {
					diff = count * count;
				}
			}

			return new Solution(potentiel + diff, m);
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
				if (score.potentiel > scoreMax.potentiel
						|| (score.potentiel == scoreMax.potentiel && score.m < scoreMax.m)) {
					max = i;
					scoreMax = score;
				}
			}

			if (n == 4) {
				switch (max) {
				case 0:
					if (logic.getPiece()[0].l < 10) {
						logic.dropPiece();
					}
					break;
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

	private int floodfill(int l, int c, int coul, GameLogic logic) {
		if (l < 0 || c < 0 || l >= logic.LINES || c >= logic.COLUMNS
				|| logic.grid[l][c] != coul) {
			return 0;
		}
		logic.grid[l][c] = -1;

		return 1 + floodfill(l + 1, c, coul, logic)
				+ floodfill(l - 1, c, coul, logic)
				+ floodfill(l, c + 1, coul, logic)
				+ floodfill(l, c - 1, coul, logic);
	}

	public void choice1() {
		logic.gridFF = new boolean[logic.LINES][logic.COLUMNS];
		int[][] grid = logic.getGrid();
		potentiel = 0;
		for (int l = 0; l < logic.LINES; l++) {
			for (int c = 0; c < logic.COLUMNS; c++) {
				if (grid[l][c] > 0 && !logic.gridFF[l][c]) {
					int count = logic.floodfill(l, c, grid[l][c], null);
					if (count > 1) {
						potentiel += count * count;
					}
				}
			}
		}

		choice(4, logic, 0);
	}

	public void update(float delta) {
		if (logic.getState() == State.MOVE) {
			sum += delta;
			if (sum > 0.2) {
				sum = 0;
				choice1();
			}
		}
	}
}
