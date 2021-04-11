package org.um.dke.titan.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.um.dke.titan.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mission To  Titan";
		config.resizable = false;
		config.vSyncEnabled = true;
		config.width = 1260;
		config.height = 720;
		new LwjglApplication(new Game(), config);
	}
}
