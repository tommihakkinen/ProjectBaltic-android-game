package com.mygdx.tikoprojectbaltic.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.tikoprojectbaltic.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.pauseWhenBackground = true;
		config.pauseWhenMinimized = true;
		config.title = "Project Baltic";
		config.height = 450;
		config.width = 800;
		config.x = 0;
		config.y = 0;
		new LwjglApplication(new Main(), config);
	}
}
