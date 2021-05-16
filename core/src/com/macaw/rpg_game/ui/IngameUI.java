package com.macaw.rpg_game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.macaw.rpg_game.entity.Player;

public class IngameUI {
	
	private Player player;
	private Texture healthBar;
	private Texture manaBar;
	private Texture background;
	private BitmapFont font;
	
	private static int BAR_HEIGHT = 15;
	private static int BORDER_SIZE = 15;
	
	private int barX = 50;
	private int healthY = 100;
	private int manaY = 50;
	
	private int backX, backY, backWidth, backHeight;
	
	public IngameUI(Player p, BitmapFont font) {
		this.font = font;
		player = p;
		healthBar = createSimpleTex(p.getMaxHealth(), BAR_HEIGHT, Color.RED);
		manaBar = createSimpleTex(p.getMaxMana(), BAR_HEIGHT, Color.BLUE);
		backX = barX - BORDER_SIZE;
		backY = Math.min(healthY, manaY) - BORDER_SIZE;
		backWidth = p.getMaxHealth() + (BORDER_SIZE * 2);
		backHeight = BAR_HEIGHT  * 8;
		background = createSimpleTex(backWidth, backHeight, Color.GRAY);
	}
	
	public void drawBars(SpriteBatch batch) {
		font.setColor(Color.BLACK);
		font.getData().setScale(0.8f);
		batch.draw(background, backX, backY, backWidth , backHeight);
		font.draw(batch, "Health: " + player.getHealth(), barX, healthY + (BAR_HEIGHT * 3));
		batch.draw(healthBar, barX, healthY, player.getHealth(), BAR_HEIGHT);
		font.draw(batch, "Mana: " + player.getMana(), barX, manaY + (BAR_HEIGHT * 3));
		batch.draw(manaBar, barX, manaY, player.getMana(), BAR_HEIGHT);
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
