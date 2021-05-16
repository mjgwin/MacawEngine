package com.macaw.rpg_game.world;


import com.badlogic.gdx.math.Rectangle;

public class Block {

	private int id;
	private float x, y, width, height;
	private Rectangle hitbox;
	
	public Block(float x, float y, float width, float height, int id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		hitbox = new Rectangle(x, y, width, height);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	
	
	
}
