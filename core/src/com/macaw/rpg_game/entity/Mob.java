package com.macaw.rpg_game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.macaw.rpg_game.utils.TextureUtils;

public class Mob {

	private float x, y, width, height;
	private Texture texture;
	private Texture damageTex;
	private Texture currTex;
	private float speed = 0.1f;
	private Rectangle hitbox;
	private float health;
	private boolean exists;
	
	
	public Mob(float x, float y, float width, float height, String normalPath, String damagePath) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		exists = true;
		health = 5f;
		hitbox = new Rectangle(x, y, width, height);
		String dir = TextureUtils.getInternalPath(normalPath);
		texture = new Texture(Gdx.files.internal(dir));
		dir = dir.replace(normalPath, damagePath);
		currTex = texture;
		damageTex = new Texture(Gdx.files.internal(dir));
	}
	
	public void move(Player p) {
		currTex = texture;
		if(!exists) return;
		if(x < p.getX()) {
			x += speed;
		}else {
			x -= speed;
		}
		
		if(y < p.getY()) {
			y += speed;
		}else {
			y -= speed;
		}
		hitbox.set(x, y, width, height);
	}
	
	public void render(SpriteBatch batch) {
		if(!exists) return;
		batch.draw(currTex, x, y, width, height);
	}
	
	public void takeDamage(float damage) {
		health -= damage;
		currTex = damageTex;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public void destroy() {
		exists = false;
	}
	
	public float getHealth() {
		return health;
	}
}
