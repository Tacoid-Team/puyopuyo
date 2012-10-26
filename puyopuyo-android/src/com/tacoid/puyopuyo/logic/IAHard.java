package com.tacoid.puyopuyo.logic;

public class IAHard {
	float sum = 0f;
	private GameLogic logic;
	private int potentiel;

	public IAHard(GameLogic logic) {
		this.logic = logic;
	}

	private class Solution implements Comparable<Solution> {
		private int potentiel;
		private int m;
		private int points;
		private int h;

		public Solution(int points, int potentiel, int h, int m) {
			this.points = points;
			this.potentiel = potentiel;
			this.h = h;
			this.m = m;
		}

		@Override
		public int compareTo(Solution another) {
			if (points != another.points) {
				return points - another.points;
			} else if (potentiel != another.potentiel) {
				return potentiel - another.potentiel;
			} else if (h != another.h) {
				return another.h - h;
			} else {
				return another.m - m;
			}
		}
	}

	private Solution choice(int n, GameLogic cl, int m) {
		if (n == 0) {
			cl.poseEtGravity();
			
			int points = cl.descendreEtPose();

			potentiel = 0;
			cl.gridFF = new boolean[cl.LINES][cl.COLUMNS];
			for (int l = 0; l < cl.LINES; l++) {
				for (int c = 0; c < cl.COLUMNS; c++) {
					if (cl.grid[l][c] > 0 && cl.grid[l][c] != cl.GARBAGE) {
						int count = floodfill(l, c, cl.grid[l][c], cl);
						if (count > 1) {
							potentiel += count * count;
						}
					}
				}
			}
			/*
			if (cl.getPiece()[0].coul != cl.getPiece()[1].coul) {
				for (Coord c : cl.getPiece()) {
					int count = floodfill(c.l, c.c, c.coul, cl);
					if (count > 2) {
						diff += -(count - 1) * (count - 1) + count * count;
					} else if (count == 2) {
						diff += count * count;
					}
				}
			} else {
				Coord c = cl.getPiece()[0];
				int count = floodfill(c.l, c.c, c.coul, cl);
				diff = -(count - 2) * (count - 2) + count * count;
			}*/

			int h = hauteur(cl);

			return new Solution(points, potentiel, h, m);
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
				if (score.compareTo(scoreMax) > 0) {
					max = i;
					scoreMax = score;
				}
			}

			if (n == 4) {

				switch (max) {
				case 0:
					logic.down();
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

	private int hauteur(GameLogic logic) {
		int l = logic.LINES - 1;
		while (l >= 0) {
			for (int c = 0; c < logic.COLUMNS; c++) {
				if (logic.grid[l][c] != 0) {
					return l + 1;
				}
			}
			l--;
		}
		return 0;
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
