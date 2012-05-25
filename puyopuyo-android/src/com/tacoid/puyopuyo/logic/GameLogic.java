package com.tacoid.puyopuyo.logic;

import java.util.ArrayList;

public class GameLogic {

	public final int LINES = 12;
	public final int COLUMNS = 6;

	private State state = State.MOVE;

	private int grid[][] = new int[LINES + 1][COLUMNS];
	private Coord[] piece = new Coord[2];
	private Coord[] nextPiece = new Coord[2];
	private int rot = 0;
	private int nextRot = 0;

	private boolean gridFF[][];
	private float sum = 0f;
	private int score = 0;
	private ArrayList<Falling> fallings;
	private float speed = 0.4f;

	private boolean first;
	private ArrayList<ArrayList<Coord>> removes;
	private int combo = 1;
	private boolean isDown = false;

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
			nextPiece[0] = new Coord(LINES - 2, COLUMNS / 2, coul1);
			nextPiece[1] = new Coord(LINES - 1, COLUMNS / 2, coul2);
			break;
		case 1:
			nextPiece[0] = new Coord(LINES - 1, COLUMNS / 2, coul1);
			nextPiece[1] = new Coord(LINES - 1, COLUMNS / 2 + 1, coul2);
			break;
		case 2:
			nextPiece[0] = new Coord(LINES - 1, COLUMNS / 2, coul1);
			nextPiece[1] = new Coord(LINES - 2, COLUMNS / 2, coul2);
			break;
		case 3:
			nextPiece[0] = new Coord(LINES - 1, COLUMNS / 2 + 1, coul1);
			nextPiece[1] = new Coord(LINES - 1, COLUMNS / 2, coul2);
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
		for (int l = 0; l < LINES; l++) {
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

	private int floodfill(int l, int c, int coul, ArrayList<Coord> list) {
		if (l < 0 || c < 0 || l >= LINES || c >= COLUMNS || grid[l][c] != coul
				|| gridFF[l][c]) {
			return 0;
		}
		gridFF[l][c] = true;
		list.add(new Coord(l, c, coul));
		return 1 + floodfill(l + 1, c, coul, list)
				+ floodfill(l - 1, c, coul, list)
				+ floodfill(l, c + 1, coul, list)
				+ floodfill(l, c - 1, coul, list);
	}

	private ArrayList<ArrayList<Coord>> resolve() {
		ArrayList<ArrayList<Coord>> remove = new ArrayList<ArrayList<Coord>>();
		gridFF = new boolean[LINES][COLUMNS];
		for (int l = 0; l < LINES; l++) {
			for (int c = 0; c < COLUMNS; c++) {
				if (grid[l][c] > 0) {
					ArrayList<Coord> list = new ArrayList<Coord>();
					if (floodfill(l, c, grid[l][c], list) >= 4) {
						remove.add(list);
						for (Coord coord : list) {
							grid[coord.l][coord.c] = 0;
						}
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

	public void descendreEtPose() {
		pose();

		combo = 1;
		do {
			fallings = gravity();
			for (Falling f : fallings) {
				grid[f.getEnd().l][f.getEnd().c] = f.getEnd().coul;
			}

			removes = resolve();
			for (ArrayList<Coord> r : removes) {
				score += r.size() * 10 * (r.size() - 3) * combo;
			}
			combo *= 2;
		} while (removes.size() > 0);
	}

	public void update(float delta) {
		sum += delta;
		switch (state) {
		case MOVE:
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
				for (ArrayList<Coord> r : removes) {
					score += r.size() * 10 * (r.size() - 3) * combo;
				}
				first = false;
			}
			if (sum > speed) {
				if (removes.size() > 0) {
					state = State.GRAVITY;
					first = true;
					combo *= 2;
				} else {
					combo = 1;
					if (generate()) {
						state = State.MOVE;
					} else {
						state = State.LOST;
					}
				}
				sum = 0f;
			}
			break;
		}
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
}
