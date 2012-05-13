package com.tacoid.puyopuyo.logic;

public class Falling {
	private Coord initial;
	private Coord end;
	private float remaining;
	
	public Falling(Coord initial, int remaining) {
		this.initial = initial;
		this.remaining = remaining;
		this.end = new Coord(initial.l - remaining, initial.c, initial.coul);
	}
	
	public void update(float delta) {
		if (this.remaining > 0) {
			this.remaining -= 10 * delta;
		} 
		if (this.remaining < 0) {
			this.remaining = 0;
		}
	}

	public Coord getInitial() {
		return initial;
	}

	public float getRemaining() {
		return remaining;
	}
	
	public Coord getEnd() {
		return end;
	}
}
