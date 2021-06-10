package org.um.dke.titan.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.um.dke.titan.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mission To  Titan";
		config.resizable = false;
		config.vSyncEnabled = true;
		config.width = 1920;
		config.height = 1080;
		config.foregroundFPS = 120;
		new LwjglApplication(new Game(), config);
	}
}
