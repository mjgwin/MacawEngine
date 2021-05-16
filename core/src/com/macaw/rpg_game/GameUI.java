package com.macaw.rpg_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.macaw.rpg_game.ui.MainMenuScreen;

public class GameUI extends Game{
	

	@Override
	public void create() {
		setScreen(new MainMenuScreen(this));
	}

}
