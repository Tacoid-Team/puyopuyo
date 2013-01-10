package com.tacoid.pweek.logic;

public class Coord {
	public float l;
	public float c;
	public int coul;
	public double angle; // angle explosion.
	
	public Coord(Coord old) {
		this.l = old.l;
		this.c = old.c;
		this.coul = old.coul;
		this.angle = old.angle;
	}
	
	public Coord(float l, float c, int coul) {
		this.l = l;
		this.c = c;
		this.coul = coul;
		this.angle = Math.random() * Math.PI;
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
