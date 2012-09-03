package com.tacoid.puyopuyo;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class PuyoPuyoDesktop {
	public static void main(String[] argv) {
		//new JoglApplication(new SuperFlu(), "SuperFlu", 800, 480, false);
		new JoglApplication(PuyoPuyo.getInstance(), "Puyo Puyo", 768, 1280, false);
	}
}