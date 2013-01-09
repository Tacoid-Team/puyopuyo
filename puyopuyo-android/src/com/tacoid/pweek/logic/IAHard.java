package com.tacoid.pweek.logic;

public class IAHard implements IA {
	float sum = 0f;
	private GameLogic logic;
	private int potentiel;
	
	private Solution dyn[][];

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
				// On choisit ce qui rapporte le plus de points.
				return points - another.points;
			} else if (points > 0 || Math.abs(potentiel - 2 * h - (another.potentiel - 2 * another.h)) < 2) {
				// Si 2 solutions rapportent des points, ou que le potentiel est à peu près identique on prend le plus sûr.
				return another.m - m;			
			} else {
				// Si aucun ne rapporte de point et potentiel bien différent, on utilise le potentiel.
				return potentiel - 2 * h - (another.potentiel - 2 * another.h);
			}
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
				return new Solution(-1, 0, 0, 0);
			}
		}
		
		if (n == 0) {
			cl.poseEtGravity();
			
			int points = cl.descendreEtPose();

			potentiel = 0;
			cl.gridFF = new boolean[GameLogic.LINES][GameLogic.COLUMNS];
			for (int l = 0; l < GameLogic.LINES; l++) {
				for (int c = 0; c < GameLogic.COLUMNS; c++) {
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

			dyn[(int)p1.l * GameLogic.COLUMNS + (int)p1.c][r] = new Solution(points, potentiel, h, m);
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
				if (logics[i].getPiece().coords[0].equals(logic.getPiece().coords[0]) && logics[i].getPiece().coords[1].equals(logic.getPiece().coords[1])) {
					// Si le déplacement ou la rotation a marché, sinon ça sert à rien.
					continue;
				}
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

			dyn[(int)p1.l * GameLogic.COLUMNS + (int)p1.c][r] = scoreMax;
			return dyn[(int)p1.l * GameLogic.COLUMNS + (int)p1.c][r];
		}
	}

	private int hauteur(GameLogic logic) {
		int l = GameLogic.LINES - 1;
		while (l >= 0) {
			for (int c = 0; c < GameLogic.COLUMNS; c++) {
				if (logic.grid[l][c] != 0) {
					return l + 1;
				}
			}
			l--;
		}
		return 0;
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
