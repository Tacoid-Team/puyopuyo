package com.tacoid.pweek.logic;

import java.util.ArrayList;

public class Explosion {
	private int nbPuyos;
	private int[][] DIR = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
	private ArrayList<Coord> removes;
	private long explosionDate = 0;
	public int points = 0;
	public double angle;

	public Explosion(GameLogic logic, ArrayList<Coord> removes) {
		this.nbPuyos = removes.size();
		this.removes = removes;
		this.explosionDate = System.currentTimeMillis();

		ArrayList<Coord> removeGarbage = new ArrayList<Coord>();
		
		for (Coord coord : removes) {
			for (int[] dir : DIR) {
				if (coord.l + dir[0] >= 0 && coord.l + dir[0] < GameLogic.LINES
						&& coord.c + dir[1] >= 0
						&& coord.c + dir[1] < GameLogic.COLUMNS) {
					if (logic.grid[(int)coord.l + dir[0]][(int)coord.c + dir[1]] == logic.GARBAGE) {
						removeGarbage.add(new Coord(coord.l + dir[0], coord.c + dir[1], logic.GARBAGE));
					}
				}
			}
		}
		
		removes.addAll(removeGarbage);
		
		for (Coord coord : removes) {
			logic.grid[(int)coord.l][(int)coord.c] = 0;
		}
	}

	public int getNbPuyos() {
		return nbPuyos;
	}
	
	public ArrayList<Coord> getExplosions() {
		return removes;
	}
	
	public long getExplosionDate() {
		return explosionDate;
	}
}