package com.tacoid.puyopuyo.logic;

import java.util.ArrayList;

public class GameLogic {

	public final int LINES = 12;
	public final int COLUMNS = 6;
	public final int GARBAGE = 5;

	private State state = State.MOVE;

	private int grid[][] = new int[LINES * 2][COLUMNS];
	private Coord[] piece = new Coord[2];
	private Coord[] nextPiece = new Coord[2];
	private int rot = 0;
	private int nextRot = 0;

	public boolean gridFF[][];
	private float sum = 0f;
	private int score = 0;
	private ArrayList<Falling> fallings;
	private float speed = 0.4f;

	private boolean first = true;
	private ArrayList<Explosion> removes;
	private int combo = 1;
	private boolean isDown = false;
	private GameLogic opponent = null;
	private int points = 0;
	private int garbage = 0;
	private float leftoverNuisance = 0f;

	private int[][] DIR = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

	private class Explosion {
		private int nbPuyos;

		public Explosion(GameLogic logic, ArrayList<Coord> removes) {
			this.nbPuyos = removes.size();

			ArrayList<Coord> removeGarbage = new ArrayList<Coord>();
			
			for (Coord coord : removes) {
				for (int[] dir : DIR) {
					if (coord.l + dir[0] >= 0 && coord.l + dir[0] < logic.LINES
							&& coord.c + dir[1] >= 0
							&& coord.c + dir[1] < logic.COLUMNS) {
						if (logic.grid[coord.l + dir[0]][coord.c + dir[1]] == GARBAGE) {
							removeGarbage.add(new Coord(coord.l + dir[0], coord.c + dir[1], GARBAGE));
						}
					}
				}
			}
			
			removes.addAll(removeGarbage);
			
			for (Coord coord : removes) {
				grid[coord.l][coord.c] = 0;
			}
		}

		public int getNbPuyos() {
			return nbPuyos;
		}
	}

	public GameLogic() {
		for (int l = 0; l < LINES; l++) {
			for (int c = 0; c < COLUMNS; c++) {
				grid[l][c] = 0;
			}
		}
		generate(); // generate current
		generate(); // generate next
	}

	public GameLogic(GameLogic logic) {
		// copy grid.
		for (int l = 0; l < LINES; l++) {
			for (int c = 0; c < COLUMNS; c++) {
				grid[l][c] = logic.grid[l][c];
			}
		}

		// piece + next piece :
		piece[0] = new Coord(logic.piece[0].l, logic.piece[0].c,
				logic.piece[0].coul);
		piece[1] = new Coord(logic.piece[1].l, logic.piece[1].c,
				logic.piece[1].coul);
		nextPiece[0] = new Coord(logic.nextPiece[0].l, logic.nextPiece[0].c,
				logic.nextPiece[0].coul);
		nextPiece[1] = new Coord(logic.nextPiece[1].l, logic.nextPiece[1].c,
				logic.nextPiece[1].coul);
		rot = logic.rot;
		nextRot = logic.nextRot;

		state = logic.state;
		combo = 1;
	}

	private boolean generate() {
		// Sens de la piece :
		// [x] [x][ ] [ ] [ ][x]
		// [ ] [x]
		piece[0] = nextPiece[0];
		piece[1] = nextPiece[1];
		rot = nextRot;

		int coul1 = 1 + (int) (Math.random() * 4);
		int coul2 = 1 + (int) (Math.random() * 4);

		nextRot = (int) (Math.random() * 4);

		switch (nextRot) {
		case 0:
			nextPiece[0] = new Coord(LINES - 1, COLUMNS / 2, coul1);
			nextPiece[1] = new Coord(LINES, COLUMNS / 2, coul2);
			break;
		case 1:
			nextPiece[0] = new Coord(LINES, COLUMNS / 2, coul1);
			nextPiece[1] = new Coord(LINES, COLUMNS / 2 + 1, coul2);
			break;
		case 2:
			nextPiece[0] = new Coord(LINES, COLUMNS / 2, coul1);
			nextPiece[1] = new Coord(LINES - 1, COLUMNS / 2, coul2);
			break;
		case 3:
			nextPiece[0] = new Coord(LINES, COLUMNS / 2 + 1, coul1);
			nextPiece[1] = new Coord(LINES, COLUMNS / 2, coul2);
			break;
		}

		return (piece[0] == null)
				|| (grid[piece[0].l][piece[0].c] == 0 && grid[piece[1].l][piece[1].c] == 0);
	}

	public void rotateRight() {
		if (state == State.MOVE) {
			Coord newPiece[] = new Coord[2];
			newPiece[0] = new Coord(piece[0].l, piece[0].c, piece[0].coul);
			newPiece[1] = new Coord(piece[1].l, piece[1].c, piece[1].coul);

			int decL = 0;
			int decC = 0;
			switch (rot) {
			case 0:
				decL = -1;
				decC = 1;
				break;
			case 1:
				decL = -1;
				decC = -1;
				break;
			case 2:
				decL = 1;
				decC = -1;
				break;
			case 3:
				decL = 1;
				decC = 1;
				break;
			}
			newPiece[1].c += decC;
			newPiece[1].l += decL;

			if (newPiece[1].c < 0) {
				newPiece[1].c++;
				newPiece[0].c++;
			}

			if (newPiece[1].c >= COLUMNS) {
				newPiece[1].c--;
				newPiece[0].c--;
			}

			if (newPiece[1].l < 0) {
				newPiece[1].l++;
				newPiece[0].l++;
			}

			if (grid[newPiece[0].l][newPiece[0].c] == 0
					&& grid[newPiece[1].l][newPiece[1].c] == 0) {
				piece[0] = newPiece[0];
				piece[1] = newPiece[1];
				rot = (rot + 1) % 4;
			}
		}
	}

	public void rotateLeft() {
		if (state == State.MOVE) {
			Coord newPiece[] = new Coord[2];
			newPiece[0] = new Coord(piece[0].l, piece[0].c, piece[0].coul);
			newPiece[1] = new Coord(piece[1].l, piece[1].c, piece[1].coul);

			int decL = 0;
			int decC = 0;
			switch (rot) {
			case 0:
				decL = -1;
				decC = -1;
				break;
			case 1:
				decL = 1;
				decC = -1;
				break;
			case 2:
				decL = 1;
				decC = 1;
				break;
			case 3:
				decL = -1;
				decC = 1;
				break;
			}
			newPiece[1].c += decC;
			newPiece[1].l += decL;

			if (newPiece[1].c < 0) {
				newPiece[1].c++;
				newPiece[0].c++;
			}

			if (newPiece[1].c >= COLUMNS) {
				newPiece[1].c--;
				newPiece[0].c--;
			}

			if (newPiece[1].l < 0) {
				newPiece[1].l++;
				newPiece[0].l++;
			}

			if (grid[newPiece[0].l][newPiece[0].c] == 0
					&& grid[newPiece[1].l][newPiece[1].c] == 0) {
				piece[0] = newPiece[0];
				piece[1] = newPiece[1];
				rot = (rot + 3) % 4;
			}
		}
	}

	public void moveDown() {
		if (state == State.MOVE) {
			descendre();
		}
	}

	public void moveLeft() {
		if (state == State.MOVE) {
			boolean ok = true;
			for (int i = 0; i < 2; i++) {
				if (piece[i].c <= 0 || grid[piece[i].l][piece[i].c - 1] > 0) {
					ok = false;
				}
			}
			if (ok) {
				for (int i = 0; i < 2; i++) {
					piece[i].c--;
				}
			}
		}
	}

	public void moveRight() {
		if (state == State.MOVE) {
			boolean ok = true;
			for (int i = 0; i < 2; i++) {
				if (piece[i].c >= COLUMNS - 1
						|| grid[piece[i].l][piece[i].c + 1] > 0) {
					ok = false;
				}
			}
			if (ok) {
				for (int i = 0; i < 2; i++) {
					piece[i].c++;
				}
			}
		}
	}

	private boolean descendre() {
		boolean result = true;
		for (Coord p : piece) {
			if (p.l <= 0 || grid[p.l - 1][p.c] > 0) {
				result = false;
			}
		}
		if (result) {
			for (Coord p : piece) {
				p.l--;
			}
		}
		return result;
	}

	private ArrayList<Falling> gravity() {
		ArrayList<Falling> boules = new ArrayList<Falling>();
		int[] sums = new int[COLUMNS];
		for (int l = 0; l < LINES * 2; l++) {
			for (int c = 0; c < COLUMNS; c++) {
				if (grid[l][c] == 0) {
					sums[c]++;
				} else if (sums[c] > 0) {
					boules.add(new Falling(new Coord(l, c, grid[l][c]), sums[c]));
					grid[l][c] = 0;
				}
			}
		}
		return boules;
	}

	public int floodfill(int l, int c, int coul, ArrayList<Coord> list) {
		if (l < 0 || c < 0 || l >= LINES || c >= COLUMNS || grid[l][c] != coul
				|| gridFF[l][c]) {
			return 0;
		}
		gridFF[l][c] = true;
		if (list != null) {
			list.add(new Coord(l, c, coul));
		}
		return 1 + floodfill(l + 1, c, coul, list)
				+ floodfill(l - 1, c, coul, list)
				+ floodfill(l, c + 1, coul, list)
				+ floodfill(l, c - 1, coul, list);
	}

	private ArrayList<Explosion> resolve() {
		ArrayList<Explosion> remove = new ArrayList<Explosion>();
		gridFF = new boolean[LINES][COLUMNS];
		for (int l = 0; l < LINES; l++) {
			for (int c = 0; c < COLUMNS; c++) {
				if (grid[l][c] > 0 && grid[l][c] != GARBAGE) {
					ArrayList<Coord> list = new ArrayList<Coord>();
					if (floodfill(l, c, grid[l][c], list) >= 4) {
						remove.add(new Explosion(this, list));
					}
				}
			}
		}
		return remove;
	}

	private void pose() {
		for (Coord p : piece) {
			grid[p.l][p.c] = p.coul;
			p.coul = 0;
		}
	}

	public void poseEtGravity() {
		pose();
		fallings = gravity();
		for (Falling f : fallings) {
			grid[f.getEnd().l][f.getEnd().c] = f.getEnd().coul;
		}
	}

	public void descendreEtPose() {
		pose();

		combo = 1;
		do {
			fallings = gravity();
			for (Falling f : fallings) {
				grid[f.getEnd().l][f.getEnd().c] = f.getEnd().coul;
			}

			removes = resolve();
			for (Explosion r : removes) {
				score += r.getNbPuyos() * 10 * (r.getNbPuyos() - 3) * combo;
			}
			combo *= 2;
		} while (removes.size() > 0);
	}

	public void update(float delta) {
		sum += delta;
		switch (state) {
		case GARBAGE:
			if (garbage > 0) {
				generateGarbage();
				state = State.GRAVITY;
			} else {
				state = State.MOVE;
			}
			first = true;
			break;
		case MOVE:
			if (first) {
				if (!generate()) {
					state = State.LOST;
				}
				first = false;
			}
			if (sum > speed || (isDown && sum > 0.1f)) {
				if (!descendre()) {
					state = State.POSE;
				}
				sum = 0f;
			}
			break;
		case POSE:
			if (sum > speed) {
				pose();
				state = State.GRAVITY;
				sum = 0f;
				first = true;
			}
			break;
		case GRAVITY:
			if (first) {
				fallings = gravity();
				first = false;
			}
			for (Falling f : fallings) {
				f.update(delta / speed);
			}
			if (sum > speed) {
				for (Falling f : fallings) {
					grid[f.getEnd().l][f.getEnd().c] = f.getEnd().coul;
				}
				fallings = null;
				first = true;
				state = State.RESOLVE;
				sum = 0f;
			}
			break;
		case RESOLVE:
			if (first) {
				removes = resolve();
				for (Explosion r : removes) {
					int p = r.getNbPuyos() * 10 * (r.getNbPuyos() - 3) * combo;
					float nuisance = p / 70.0f + leftoverNuisance;
					leftoverNuisance = nuisance - (int) nuisance;
					opponent.sendGarbage((int) nuisance);
					points += p;
				}
				first = false;
			}
			if (sum > speed) {
				if (removes.size() > 0) {
					state = State.GRAVITY;
					first = true;
					combo *= 2;
				} else {
					state = State.GARBAGE;
					score += points;
					points = 0;
					combo = 1;
				}
				sum = 0f;
			}
			break;
		}
	}

	private void generateGarbage() {
		int l = LINES;
		while (garbage >= COLUMNS) {
			for (int c = 0; c < COLUMNS; c++) {
				grid[l][c] = GARBAGE;
			}
			garbage -= COLUMNS;
			l++;
		}

		ArrayList<Integer> cols = new ArrayList<Integer>();
		for (int c = 0; c < COLUMNS; c++) {
			cols.add(c);
		}
		
		for (int c = 0; c < garbage; c++) {
			int i = (int)(Math.random() * cols.size());
			grid[l][cols.get(i)] = GARBAGE;
			cols.remove(i);
		}
		garbage = 0;
	}

	public int[][] getGrid() {
		return grid;
	}

	public Coord[] getPiece() {
		return piece;
	}

	public Coord[] getNextPiece() {
		return nextPiece;
	}

	public ArrayList<Falling> getFallings() {
		return fallings;
	}

	public State getState() {
		return state;
	}

	public int getScore() {
		return score;
	}

	public void down() {
		isDown = true;
	}

	public void up() {
		isDown = false;
	}

	public void setOpponent(GameLogic opponent) {
		this.opponent = opponent;
	}

	public void sendGarbage(int garbage) {
		this.garbage += garbage;
		if (this.garbage > LINES * COLUMNS) {
			garbage = LINES * COLUMNS;
		}
	}
}
