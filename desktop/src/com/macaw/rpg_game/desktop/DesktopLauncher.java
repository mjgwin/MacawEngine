package com.macaw.rpg_game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.macaw.rpg_game.GameUI;

/*
 * @author Micah Gwin
 * 
 * The desktop launcher is platform specific for PC,
 * config controls title, window mode and many other things.
 * The class passed into the application with the config will be ran
 * with lwjgl 3. (Input class must extend com.badlogic.gdx.Game)
 */

public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280, 720);
		config.setTitle("Zombie Tower Defense");
		config.setForegroundFPS(75);
		new Lwjgl3Application(new GameUI(), config);
	}
}
