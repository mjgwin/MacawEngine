package com.macaw.rpg_game.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.macaw.rpg_game.utils.TextureUtils;
import com.macaw.rpg_game.world.Block;

/*
 * @author Micah Gwin
 * 
 * Basic monster class for the game, which other more complex
 * monsters can inherit from. Supports both animated and static 
 * formats.
 */

public class Mob {

	private float x, y, width, height;
	private Texture texture;
	private Texture damageTex;
	private Texture currTex;
	private float speed = 0.2f;
	private Rectangle hitbox;
	private float health;
	private boolean exists;
	private boolean animated;
	private boolean isDamaged;

	private TextureRegion[][] spriteFrames;
	private Animation<TextureRegion> up, down, left, right;
	private float elapsedTime = 0f;
	private float animSpeed = 0.1f;
	private TextureRegion currRegion;
	
	private static float COLLIDE_BUFFER = 3f;

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

		for (int i = 0; i < 4; i++) {
			TextureRegion[] temp = spriteFrames[i];
			if (i == 0)
				down = new Animation<TextureRegion>(animSpeed, temp);
			if (i == 1)
				right = new Animation<TextureRegion>(animSpeed, temp);
			if (i == 2)
				up = new Animation<TextureRegion>(animSpeed, temp);
			if (i == 3)
				left = new Animation<TextureRegion>(animSpeed, temp);

		}
		currRegion = spriteFrames[0][0];

		isDamaged = false;
	}

	public void render(SpriteBatch batch) {
		if (!exists)
			return;

		if (animated) {
			if (isDamaged) {
				batch.setColor(Color.RED);
			}
			batch.draw(currRegion, x, y, width, height);
			if (isDamaged) {
				batch.setColor(Color.WHITE);
				isDamaged = false;
			}
		} else {
			batch.draw(currTex, x, y, width, height);
		}

	}

	public void takeDamage(float damage) {
		health -= damage;
		if (animated) {
			isDamaged = true;
		} else {
			currTex = damageTex;
		}

	}

	public void move(Player p, Block[][] world, ArrayList<Mob> mobs) {
		if (!exists)
			return;
		
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
		
		for(int i = 0; i < mobs.size(); i++) {
			
			if(equalsMob(mobs.get(i))) {
				//do nothing
			}else {
				if(intersectsOther(mobs.get(i), "up")) collidesTopY = true;
				if(intersectsOther(mobs.get(i), "down")) collidesBottomY = true;
				if(intersectsOther(mobs.get(i), "left")) collidesLeftX = true;
				if(intersectsOther(mobs.get(i), "right")) collidesRightX = true;
			}
			
			
		}
		

		double angle = getDistAngle(p.getX(), p.getY());
		// Scale angle between -1,1 and multiply by speed to change position
		double moveX = Math.cos(angle);
		double moveY = Math.sin(angle);
		
		if((!collidesLeftX && moveX < 0) || (!collidesRightX && moveX > 0)) {
			this.x += speed * moveX;
		}
		
		if((!collidesBottomY && moveY < 0) || (!collidesTopY && moveY > 0)) {
			this.y += speed * moveY;
		}
		
		

		if (animated) {
			elapsedTime += Gdx.graphics.getDeltaTime();

			// Scale angle between 0,360 to change direction of animated texture
			angle = Math.toDegrees(angle);
			angle = angle + Math.ceil(-angle / 360) * 360;

			if (angle > 135 && angle < 225) {
				currRegion = left.getKeyFrame(elapsedTime, true);
			} else if (angle > 315 || angle < 45) {
				currRegion = right.getKeyFrame(elapsedTime, true);
			} else if (angle > 45 && angle < 135) {
				currRegion = up.getKeyFrame(elapsedTime, true);
			} else if (angle > 225 && angle < 315) {
				currRegion = down.getKeyFrame(elapsedTime, true);
			}

		} else {
			currTex = texture;
		}

		hitbox.set(x, y, width, height);
	}
	
	private boolean intersectsOther(Mob other, String direction) {
		boolean intersects = false;
		
		float xMax = this.x + this.width / 2;
		float yMax = this.y + this.height / 2;
		
		if(direction.equals("up")) {
			intersects = other.hitbox.contains(xMax, yMax + COLLIDE_BUFFER);
		}else if(direction.equals("down")) {
			intersects = other.hitbox.contains(xMax, yMax - COLLIDE_BUFFER);
		}else if(direction.equals("left")) {
			intersects = other.hitbox.contains(xMax - COLLIDE_BUFFER, yMax);
		}else if(direction.equals("right")) {
			intersects = other.hitbox.contains(xMax + COLLIDE_BUFFER, yMax);
		}
		
		return intersects;
	}

	// Returns the angle from the mob position to the player position
	private double getDistAngle(float playerX, float playerY) {
		return Math.atan2((playerY - this.y), (playerX - this.x));
	}
	
	private boolean equalsMob(Mob other) {
		return this.x == other.x && this.y == other.y && this.width == other.width &&
				this.height == other.height && this.health == other.health;
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
	
	
}
