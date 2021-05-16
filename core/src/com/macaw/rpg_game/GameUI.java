package com.macaw.rpg_game;

import com.badlogic.gdx.Game;
import com.macaw.rpg_game.ui.MainMenuScreen;
import com.macaw.rpg_game.utils.EngineLogger;

/*
 * @author Micah Gwin
 * 
 * The GameUI class only serves to control the game state.
 * Initially it is being set to the main menu, this
 * class is then passed along to the next state when it is changed. 
 */

public class GameUI extends Game {
	
	/*
	 * Sets the initial state and initializes the logger,
	 * allowing errors to be tracked in terminal and log file
	 */

	@Override
	public void create() {
		EngineLogger.logFileSetup("logFile/MacawEngineLog.txt");
		EngineLogger.log("Game launched, first state set to MainMenuScreen");
		setScreen(new MainMenuScreen(this));
	}

}
