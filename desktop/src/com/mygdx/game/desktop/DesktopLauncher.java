package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGame;

// TODO: Auto-generated Javadoc
/**
 * The Class DesktopLauncher.
 */
public class DesktopLauncher {
	
	/**
	 * The main method.
	 *
	 * @param arg the arguments
	 */
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyGame(false), config);
	}
}
