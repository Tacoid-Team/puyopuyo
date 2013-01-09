package com.tacoid.pweek.logic;

public class Piece {
	public Coord[] coords = new Coord[2];
	public Coord[] prevCoords = new Coord[2];
	public long lastMove = 0;

	public Piece() {
		prevCoords[0] = new Coord(0, 0, 0);
		prevCoords[1] = new Coord(0, 0, 0);
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
				prevCoords[i].l = coords[i].l;
				coords[i].c++;
			}
			lastMove = System.currentTimeMillis();
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
				prevCoords[i].l = coords[i].l;
				coords[i].c--;
			}
			lastMove = System.currentTimeMillis();
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
				prevCoords[i].c = coords[i].c;
				prevCoords[i].l = coords[i].l;
				coords[i].l--;
			}
			lastMove = System.currentTimeMillis();
		}
		return result;
	}

	public Coord[] interpolatedCoord() {
		long currentDate = System.currentTimeMillis();
		
		float a = Math.min(1, (currentDate - lastMove) / 100.0f);
		
		Coord[] result = new Coord[2];
		for (int i = 0; i < 2; i++) {
			float c = prevCoords[i].c + (coords[i].c - prevCoords[i].c) * a;
			float l = prevCoords[i].l + (coords[i].l - prevCoords[i].l) * a;
			
			result[i] = new Coord(l, c, coords[i].coul);
		}
		
		return result;
	}
	
}
