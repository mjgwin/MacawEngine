package com.macaw.rpg_game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.macaw.rpg_game.utils.TextureUtils;
import com.macaw.rpg_game.world.Block;

public class Bullet {
	
	private float x, y, width, height;
	private Texture texture;
	private String direction;
	boolean exists;
	private float speed;
	private float damage;
	private int id;
	
	public Bullet(float x, float y, float width, float height, String texPath, String direction, int id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.direction = direction;
		this.id = id;
		exists = true;
		speed = 1f;
		damage = 1f;
		String dir = TextureUtils.getInternalPath(texPath);
		//System.out.println("Loaded bullet textures from: " + directory + "\\" + texPath);
		texture = new Texture(Gdx.files.internal(dir));
	}
	
	public void move() {
		if(!exists) return;
		
		if(direction.equals("up")) {
			y += speed;
		}
		
		if(direction.equals("left")) {
			x -= speed;
		}
		
		if(direction.equals("down")) {
			y -= speed;
		}
		
		if(direction.equals("right")) {
			x += speed;
		}
	}
	
	public void render(SpriteBatch batch) {
		if(!exists) return;
		batch.draw(texture, x, y, width, height);
	}
	
	public void destroy() {
		exists = false;
	}
	
	public boolean checkCollide(Block[][] world, ArrayList<Mob> mobs) {
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				Block temp = world[i][j];
				if(temp.getHitbox().contains(x, y) && temp.getId() == 1) return true;
			}
		}
		
		for(Mob m : mobs) {
			if(m.getHitbox().contains(x, y)) {
				m.takeDamage(damage);
				return true;
			}
		}
		return false;
	}
	
	public int getID() {
		return id;
	}

}
