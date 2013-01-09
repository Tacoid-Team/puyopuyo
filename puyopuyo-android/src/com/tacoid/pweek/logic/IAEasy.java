package com.tacoid.pweek.logic;

public class IAEasy implements IA {
	float sum = 0f;
	private GameLogic logic;
	private int potentiel;
	private Solution dyn[][];


	public IAEasy(GameLogic logic) {
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
		Coord p1 = cl.getPiece().coords[0];
		Coord p2 = cl.getPiece().coords[1];
		int dl = (int)(p1.l - p2.l);
		int dc = (int)(p1.c - p2.c);
		
		int r;
		if (dl == 0) {
			if (dc == 1) {
				r = 0;
			} else {
				r = 1;
			}
		} else if (dl == 1) {
			r = 2;
		} else {
			r = 3;
		}
		
		if (dyn[(int)p1.l * GameLogic.COLUMNS + (int)p1.c][r] != null) {
			// Si on a déjà été dans cette position en moins de mouvement
			if (dyn[(int)p1.l * GameLogic.COLUMNS + (int)p1.c][r].m <= m) {
				// Alors c'est naze.
				return new Solution(-1, 0);
			}
		}
		
		if (n == 0) {			
			int diff = 0;
			cl.poseEtGravity();

			for (Coord c : cl.getPiece().coords) {
				int count = floodfill((int)c.l, (int)c.c, c.coul, cl);
				if (count > 2) {
					diff = -(count - 1) * (count - 1) + count * count;
				} else if (count == 2) {
					diff = count * count;
				}
			}

			dyn[(int)p1.l * GameLogic.COLUMNS + (int)p1.c][r] = new Solution(potentiel + diff, m);
			return dyn[(int)p1.l * GameLogic.COLUMNS + (int)p1.c][r];
		} else {
			GameLogic[] logics = new GameLogic[5];

			logics[0] = new GameLogic(cl,true);
			logics[1] = new GameLogic(cl,true);
			logics[1].rotateLeft();
			logics[2] = new GameLogic(cl,true);
			logics[2].rotateRight();
			logics[3] = new GameLogic(cl,true);
			logics[3].moveLeft();
			logics[4] = new GameLogic(cl,true);
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
					if (logic.getPiece().coords[0].l < 10) {
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

			dyn[(int)p1.l * GameLogic.COLUMNS + (int)p1.c][r] = scoreMax;
			return dyn[(int)p1.l * GameLogic.COLUMNS + (int)p1.c][r];
		}
	}

	private int floodfill(int l, int c, int coul, GameLogic logic) {
		if (l < 0 || c < 0 || l >= GameLogic.LINES || c >= GameLogic.COLUMNS
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
		logic.gridFF = new boolean[GameLogic.LINES][GameLogic.COLUMNS];
		int[][] grid = logic.getGrid();
		potentiel = 0;
		for (int l = 0; l < GameLogic.LINES; l++) {
			for (int c = 0; c < GameLogic.COLUMNS; c++) {
				if (grid[l][c] > 0 && !logic.gridFF[l][c]) {
					int count = logic.floodfill(l, c, grid[l][c], null);
					if (count > 1) {
						potentiel += count * count;
					}
				}
			}
		}

		dyn = new Solution[(GameLogic.LINES + 1) * GameLogic.COLUMNS][4];
		choice(4, logic, 0);
	}

	public void update(float delta) {
		if (logic.getState() == State.MOVE && !logic.paused) {
			sum += delta;
			if (sum > 0.2) {
				sum = 0;
				choice1();
			}
		}
	}
}
