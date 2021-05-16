package com.macaw.rpg_game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.macaw.rpg_game.utils.TextureUtils;

public class BigTile {

	private float x, y, width, height;
	private Texture texture;
	private float blockSize;
	
	public BigTile(float x, float y, float width, float height, float blockSize, String path) {
		String dir = TextureUtils.getInternalPath("/worldTextures/" + path);
		texture = new Texture(Gdx.files.internal(dir));
		this.x = x * blockSize;
		this.y = y * blockSize;
		this.width = width * blockSize;
		this.height = height * blockSize;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, width, height);
	}
}
