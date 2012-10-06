package com.tacoid.puyopuyo;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class PuyoPuyoDesktop {
	public static void main(String[] argv) {
		PuyoPuyo.getInstance().setDesktopMode(true);
		new LwjglApplication(PuyoPuyo.getInstance(), "Puyo Puyo",  1280, 768, false);
	}
}