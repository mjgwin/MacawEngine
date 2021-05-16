package com.macaw.rpg_game.entity;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.macaw.rpg_game.utils.TextureUtils;
import com.macaw.rpg_game.world.Block;


public class Player {
	

	
	//private Sprite currSprite;
	//private Texture texture;
	private TextureRegion[][] spriteFrames;
	
	private float x, y, width, height;
	
	private Animation<TextureRegion> up, down, left, right;
	
	private float elapsedTime = 0f;
	private float animSpeed = 0.1f;
	
	private TextureRegion currRegion;
	
	private Rectangle hitbox;
	
	private static float COLLIDE_BUFFER = 3f;
	
	private ArrayList<Bullet> bullets;
	
	private static final long FIRE_RATE = 200000000L;
	private long lastShot;
	
	private int currBullets;
	
	private int maxHealth = 100;
	private int maxMana = 100;
	
	private int health;
	private int mana;
	
	public Player(float x, float y, float width, float height) {
		//texture = new Texture("player_transparent.png");
		//sprite = new Sprite(texture);
		initStats();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		currBullets = 0;
		
		hitbox = new Rectangle(x, y, width, height);
		bullets = new ArrayList<Bullet>();
		
		spriteFrames = TextureUtils.loadSpriteSheet("playerImg/playerSprites.png", 4, 4);
		
		for(int i = 0; i < 4; i++) {
				TextureRegion[] temp = spriteFrames[i];
				if(i==0) down = new Animation<TextureRegion>(animSpeed, temp);
				if(i==1) up = new Animation<TextureRegion>(animSpeed, temp);
				if(i==2) left = new Animation<TextureRegion>(animSpeed, temp);
				if(i==3) right = new Animation<TextureRegion>(animSpeed, temp);
				
		}
		
		//currSprite = new Sprite(spriteFrames[0][0]);
		currRegion = spriteFrames[0][0];
		//currSprite.setBounds(x, y, width, height);
	}
	
	private void initStats() {
		health = maxHealth;
		mana = maxMana;
	}
	
	public void removeBullet(int id) {
		bullets.removeIf(b -> b.getID() == id);
	}
	
	public void shoot(String direction) {

		if(System.nanoTime() - lastShot >= FIRE_RATE) {
			bullets.add(new Bullet(this.x, this.y, 9, 9, "entity/fireball.png", direction, currBullets));
			currBullets = bullets.size();
		    lastShot = System.nanoTime();
		}
	}
	
	public void updateSprite(String name) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		
		if(name.equals("up")) {
			currRegion = up.getKeyFrame(elapsedTime, true);
		}
		
		if(name.equals("down")) {
			currRegion = down.getKeyFrame(elapsedTime, true);
		}
		
		if(name.equals("left")) {
			currRegion = left.getKeyFrame(elapsedTime, true);
		}
		
		if(name.equals("right")) {
			currRegion = right.getKeyFrame(elapsedTime, true);
		}
	
	}
	
	
	
	
	
	public void render(SpriteBatch batch) {
		batch.draw(currRegion, x, y, width, height);
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void move(float x, float y, Block[][] world) {
		
		
		boolean collidesLeftX = false;
		boolean collidesRightX = false;
		boolean collidesTopY = false;
		boolean collidesBottomY = false;
		
		float xMax = this.x + this.width / 2;
		float yMax = this.y + this.height / 2;
		
		for(int j = 0; j < world.length; j++) {
			for(int i = 0; i < world[0].length; i++) {
				Block temp = world[j][i];
				if(temp.getHitbox().contains(xMax - COLLIDE_BUFFER, yMax) && (temp.getId() == 1 || temp.getId() == 3)) {
					collidesLeftX = true;
				}
				if(temp.getHitbox().contains(xMax + COLLIDE_BUFFER, yMax) && (temp.getId() == 1 || temp.getId() == 3)) {
					collidesRightX = true;
				}
				if(temp.getHitbox().contains(xMax, yMax - COLLIDE_BUFFER) && (temp.getId() == 1 || temp.getId() == 3)) {
					collidesBottomY = true;
				}
				if(temp.getHitbox().contains(xMax, yMax + COLLIDE_BUFFER) && (temp.getId() == 1 || temp.getId() == 3)) {
					collidesTopY = true;
				}
				
			}
		}
		if(x > 0 && !collidesRightX) {
			this.x += x;
		}
		if(x < 0 && !collidesLeftX) {
			this.x += x;
		}
		
		if(y > 0 && !collidesTopY) {
			this.y += y;
		}
		if(y < 0 && !collidesBottomY) {
			this.y += y;
		}
		
		hitbox.set(this.x, this.y, width, height);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public ArrayList<Bullet> getBullets(){
		return bullets;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}
	
	

}
