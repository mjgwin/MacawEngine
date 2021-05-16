package com.macaw.rpg_game.desktop;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.macaw.rpg_game.GameUI;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280, 720);
		//new GameMain() for no ui
		new Lwjgl3Application(new GameUI(), config);
	}
} 
