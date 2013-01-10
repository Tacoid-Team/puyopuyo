package com.tacoid.pweek.logic;

public class Piece {
	public int rot = 0;
	public Coord[] coords = new Coord[2];
	public Coord[] prevCoords = new Coord[2];
	public long lastMoveC = 0;
	public long lastMoveL = 0;

	public Piece() {
		coords[0] = new Coord(0, 0, 0);
		coords[1] = new Coord(0, 0, 0);
		prevCoords[0] = new Coord(0, 0, 0);
		prevCoords[1] = new Coord(0, 0, 0);
	}
	
	public Piece(Piece nextPiece) {
		this(nextPiece, false);
	}
	
	public Piece(Piece nextPiece, boolean copy) {
		if (copy) {
			coords[0] = new Coord(nextPiece.coords[0]);
			coords[1] = new Coord(nextPiece.coords[1]);
		} else {
			coords[0] = nextPiece.coords[0];
			coords[1] = nextPiece.coords[1];
		}
		prevCoords[0] = new Coord(0, 0, 0);
		prevCoords[1] = new Coord(0, 0, 0);
		rot = nextPiece.rot;
	}

	public boolean moveRight(int[][] grid) {
		boolean ok = true;
		for (int i = 0; i < 2; i++) {
			if (coords[i].c >= GameLogic.COLUMNS - 1
					|| grid[(int)coords[i].l][(int)coords[i].c + 1] > 0) {
				ok = false;
			}
		}
		if (ok) {
			for (int i = 0; i < 2; i++) {
				prevCoords[i].c = coords[i].c;
				//prevCoords[i].l = coords[i].l;
				coords[i].c++;
			}
			lastMoveC = System.currentTimeMillis();
		}
		return ok;
	}

	public boolean moveLeft(int[][] grid) {
		boolean ok = true;
		for (int i = 0; i < 2; i++) {
			if (coords[i].c <= 0 || grid[(int)coords[i].l][(int)coords[i].c - 1] > 0) {
				ok = false;
			}
		}
		if (ok) {
			for (int i = 0; i < 2; i++) {
				prevCoords[i].c = coords[i].c;
				//prevCoords[i].l = coords[i].l;
				coords[i].c--;
			}
			lastMoveC = System.currentTimeMillis();
		}
		return ok;
	}
	
	public boolean descendre(int[][] grid) {
		boolean result = true;
		for (Coord p : coords) {
			if (p.l <= 0 || grid[(int)p.l - 1][(int)p.c] > 0) {
				result = false;
			}
		}
		if (result) {
			for (int i = 0; i < 2; i++) {
				//prevCoords[i].c = coords[i].c;
				prevCoords[i].l = coords[i].l;
				coords[i].l--;
			}
			lastMoveL = System.currentTimeMillis();
		}
		return result;
	}

	public Coord[] interpolatedCoord() {
		long currentDate = System.currentTimeMillis();
		
		float aC = Math.min(1, (currentDate - lastMoveC) / 100.0f);
		float aL = Math.min(1, (currentDate - lastMoveL) / 100.0f);
		
		Coord[] result = new Coord[2];
		for (int i = 0; i < 2; i++) {
			float c = prevCoords[i].c + (coords[i].c - prevCoords[i].c) * aC;
			float l = prevCoords[i].l + (coords[i].l - prevCoords[i].l) * aL;
			
			result[i] = new Coord(l, c, coords[i].coul);
		}
		
		return result;
	}

	public void rotateLeft(int[][] grid) {
		Coord newPiece[] = new Coord[2];
		newPiece[0] = new Coord(coords[0].l, coords[0].c, coords[0].coul);
		newPiece[1] = new Coord(coords[1].l, coords[1].c, coords[1].coul);

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

		if (newPiece[1].c >= GameLogic.COLUMNS) {
			newPiece[1].c--;
			newPiece[0].c--;
		}

		if (newPiece[1].l < 0) {
			newPiece[1].l++;
			newPiece[0].l++;
		}

		if (grid[(int)newPiece[0].l][(int)newPiece[0].c] == 0
				&& grid[(int)newPiece[1].l][(int)newPiece[1].c] == 0) {
			coords[0] = newPiece[0];
			coords[1] = newPiece[1];
			rot = (rot + 3) % 4;
		}
	}

	public void rotateRight(int[][] grid) {
		Coord newPiece[] = new Coord[2];
		newPiece[0] = new Coord(coords[0].l, coords[0].c, coords[0].coul);
		newPiece[1] = new Coord(coords[1].l, coords[1].c, coords[1].coul);

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

		if (newPiece[1].c >= GameLogic.COLUMNS) {
			newPiece[1].c--;
			newPiece[0].c--;
		}

		if (newPiece[1].l < 0) {
			newPiece[1].l++;
			newPiece[0].l++;
		}

		if (grid[(int)newPiece[0].l][(int)newPiece[0].c] == 0
				&& grid[(int)newPiece[1].l][(int)newPiece[1].c] == 0) {
			coords[0] = newPiece[0];
			coords[1] = newPiece[1];
			rot = (rot + 1) % 4;
		}
	}
	
}
