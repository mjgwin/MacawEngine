package com.macaw.rpg_game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.macaw.rpg_game.entity.Player;
import com.macaw.rpg_game.world.World;

public class IngameUI {
	
	private World world;
	private Player player;
	private Texture healthBar, healthBackground;
	private Texture manaBar, manaBackground;
	private Texture middleBar;
	private Texture background;
	private BitmapFont font;
	
	private static int BAR_HEIGHT = 15;
	private static int BORDER_SIZE = 15;
	
	private int barX = 50;
	private int healthY = 100;
	private int manaY = 45;
	private int barBorder = 5;
	private int backX, backY;
	private int backWidth = 300;
	private int backHeight = 120;
	
	public IngameUI(World w, BitmapFont font) {
		this.font = font;
		world = w;
		player = w.getPlayer();
		healthBar = createSimpleTex(100, BAR_HEIGHT, Color.RED);
		manaBar = createSimpleTex(100, BAR_HEIGHT, Color.BLUE);
		healthBackground = createSimpleTex(100 - barBorder, BAR_HEIGHT - barBorder, Color.BLACK);
		manaBackground = createSimpleTex(100 - barBorder, BAR_HEIGHT - barBorder, Color.BLACK);
		middleBar = createSimpleTex(5, backHeight, Color.BLACK);
		backX = barX - BORDER_SIZE;
		backY = Math.min(healthY, manaY) - BORDER_SIZE;
		background = createSimpleTex(backWidth, backHeight, Color.TAN);
	}
	
	public void draw(SpriteBatch batch) {
		font.setColor(Color.BLACK);
		font.getData().setScale(0.9f);
		batch.draw(background, backX, backY, backWidth , backHeight);
		batch.draw(middleBar, backX + backWidth / 2, backY);
		font.draw(batch, "Health: " + player.getHealth(), barX, healthY + (BAR_HEIGHT * 3));
		batch.draw(healthBackground, barX - barBorder, healthY - barBorder , player.getHealth() + barBorder * 2, BAR_HEIGHT + barBorder * 2);
		batch.draw(healthBar, barX, healthY, player.getHealth(), BAR_HEIGHT);
		font.draw(batch, "Mana: " + player.getMana(), barX, manaY + (BAR_HEIGHT * 3));
		batch.draw(manaBackground, barX - barBorder, manaY - barBorder, player.getMana() + barBorder * 2, BAR_HEIGHT + barBorder * 2);
		batch.draw(manaBar, barX, manaY, player.getMana(), BAR_HEIGHT);
		
		font.draw(batch, "Wave: " + world.getWave(), 220, healthY + (BAR_HEIGHT * 3));
	}
	
	private Texture createSimpleTex(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, width, height);
        Texture tex = new Texture(pixmap);
        pixmap.dispose();
        return tex;
    }

}
