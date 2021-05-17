package com.macaw.rpg_game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private boolean animated;
	
	private TextureRegion[][] spriteFrames;
	private Animation<TextureRegion> up, down, left, right;
	private float elapsedTime = 0f;
	private float animSpeed = 0.1f;
	private TextureRegion currRegion;
	
	public Mob(float x, float y, float width, float height, String normalPath, String damagePath) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		animated = false;
		exists = true;
		health = 5f;
		hitbox = new Rectangle(x, y, width, height);
		String dir = TextureUtils.getInternalPath(normalPath);
		texture = new Texture(Gdx.files.internal(dir));
		dir = dir.replace(normalPath, damagePath);
		currTex = texture;
		damageTex = new Texture(Gdx.files.internal(dir));
	}
	
	public Mob(float x, float y, float width, float height, String sheetPath) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		animated = true;
		exists = true;
		health = 5f;
		hitbox = new Rectangle(x, y, width, height);
		
		spriteFrames = TextureUtils.loadSpriteSheet(sheetPath, 3, 4);
		
		for(int i = 0; i < 4; i++) {
				TextureRegion[] temp = spriteFrames[i];
				if(i==0) down = new Animation<TextureRegion>(animSpeed, temp);
				if(i==1) up = new Animation<TextureRegion>(animSpeed, temp);
				if(i==2) left = new Animation<TextureRegion>(animSpeed, temp);
				if(i==3) right = new Animation<TextureRegion>(animSpeed, temp);
				
		}
		currRegion = spriteFrames[0][0];
	}
	
	public void move(Player p) {
		
		if(!exists) return;
		
		if(animated) {
			elapsedTime += Gdx.graphics.getDeltaTime();
			
			if(x < p.getX()) {
				x += speed;
				currRegion = right.getKeyFrame(elapsedTime, true);
			}else {
				x -= speed;
				currRegion = left.getKeyFrame(elapsedTime, true);
			}
			
			if(y < p.getY()) {
				y += speed;
				currRegion = up.getKeyFrame(elapsedTime, true);
			}else {
				y -= speed;
				currRegion = down.getKeyFrame(elapsedTime, true);
			}
			
		}else {
			currTex = texture;
			
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
		}
		
		
		
		hitbox.set(x, y, width, height);
	}
	
	public void render(SpriteBatch batch) {
		if(!exists) return;
		
		if(animated) {
			batch.draw(currRegion, x, y, width, height);
		}else {
			batch.draw(currTex, x, y, width, height);
		}
		
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
