package com.tacoid.pweek.logic;

public class Coord {
	public int l;
	public int c;
	public int coul;
	
	public Coord(int l, int c, int coul) {
		this.l = l;
		this.c = c;
		this.coul = coul;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Coord) {
			Coord ot = (Coord)o;
			return l == ot.l && c == ot.c && coul == ot.coul;
		}
		return false;
	}
}
