package com.macaw.rpg_game.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.macaw.rpg_game.entity.BigTile;
import com.macaw.rpg_game.entity.Bullet;
import com.macaw.rpg_game.entity.Mob;
import com.macaw.rpg_game.entity.Player;
import com.macaw.rpg_game.ui.IngameUI;
import com.macaw.rpg_game.utils.DungeonGenerator;
import com.macaw.rpg_game.utils.InputHandler;
import com.macaw.rpg_game.utils.TeleportData;
import com.macaw.rpg_game.utils.TextureUtils;


/*
 * @author Micah Gwin
 * 
 * Main data structure in the game that contains entities,
 * the player and all world tiles and bigTiles.
 * Methods to update these objects or change state
 * to a new world.
 */


public class World {

	private Block[][] blocks;
	private OrthographicCamera camera;
	private int width, height;
	private float blockSize;
	private float cameraSpeed;
	private InputHandler input;

	private ArrayList<Mob> mobs;
	private ArrayList<BigTile> bigTiles;
	private ArrayList<TeleportData> tpLocations;
	private Player player;

	
	private boolean loading = false;
	
	

	public World(int width, int height, float blockSize, OrthographicCamera camera, float cameraSpeed) {
		if (camera == null || cameraSpeed == 0 || width == 0 || height == 0 || blockSize == 0) {
			throw new IllegalStateException("Error: check args in World constructor");
		}
		createEntityLists();
		this.width = width;
		this.height = height;
		this.blockSize = blockSize;
		this.camera = camera;
		this.cameraSpeed = cameraSpeed;
		blocks = new Block[width][height];
		//createEmpty();
		
		DungeonGenerator generator = new DungeonGenerator(width, height, blockSize, 20);
		blocks = generator.getMap();
		spawnPlayerInValidRoom(generator.getRandomPlayerSpawn());
	}

	public World(String path, int width, int height, float blockSize, OrthographicCamera camera, float cameraSpeed) {
		if (path == null || camera == null || cameraSpeed == 0 || width == 0 || height == 0 || blockSize == 0) {
			throw new IllegalStateException("Error: check args in World constructor");
		}
		createEntityLists();
		this.width = width;
		this.height = height;
		this.blockSize = blockSize;
		this.camera = camera;
		this.cameraSpeed = cameraSpeed;
		blocks = new Block[width][height];
		loadFromFile(path);
	}

	public World(String path, OrthographicCamera camera, float cameraSpeed) {
		if (path == null || camera == null || cameraSpeed == 0) {
			throw new IllegalStateException("Error: check args in World constructor");
		}
		createEntityLists();
		this.camera = camera;
		this.cameraSpeed = cameraSpeed;
		loadFromFile(path);
	}

	public void spawnPlayer(int x, int y) {
		player = new Player(x * blockSize, y * blockSize, 20, 20);
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
	}
	
	private void spawnPlayerInValidRoom(Vector2 spawn) {
		player = new Player(spawn.x, spawn.y, 20, 20);
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
	}

	

	public World changeState(String path, OrthographicCamera camera, float cameraSpeed) {
		return new World(path, camera, cameraSpeed);
	}


	private void createEntityLists() {
		bigTiles = new ArrayList<BigTile>();
		tpLocations = new ArrayList<TeleportData>();
		input = new InputHandler();
		mobs = new ArrayList<Mob>();
	}

	

	private void createEmpty() {
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				blocks[j][i] = new Block(j * blockSize, i * blockSize, blockSize, blockSize, 0);
			}
		}
	}

	public void loadFromFile(String path) {
		String dir = TextureUtils.getInternalPath(path);

		try {
			File f = new File(dir);
			BufferedReader buffer = new BufferedReader(new FileReader(f));
			String line;
			int row = 0;
			while ((line = buffer.readLine()) != null) {
				String[] vals = line.trim().split("\\s+");
				if (line.contains("blockSize")) {
					blockSize = Integer.parseInt(vals[1]);
				} else if (line.contains("height")) {
					height = Integer.parseInt(vals[1]);
					if(width != 0 && height != 0) {
						blocks = new Block[width][height];
					}
				} else if (line.contains("width")) {
					width = Integer.parseInt(vals[1]);
					if(width != 0 && height != 0) {
						blocks = new Block[width][height];
					}
				} else if (line.contains("bigTile")) {
					loadBigTile(vals);
				} else if (line.contains("tp")) {
					loadTP(vals);
				} else {
					for (int col = 0; col < width; col++) {
						blocks[col][row] = new Block(col * blockSize, (width * blockSize) - row * blockSize, blockSize,
								blockSize, Integer.parseInt(vals[col]));
					}

					row++;
				}
			}
			buffer.close();
			if (width == 0 || height == 0) {
				throw new IllegalStateException("Error: must init width and height in world constructor/file");
			}
		} catch (IOException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void loadBigTile(String[] vals) {
		int x = Integer.parseInt(vals[1]);
		int y = Integer.parseInt(vals[2]);
		int width = Integer.parseInt(vals[3]);
		int height = Integer.parseInt(vals[4]);
		String bigTilePath = vals[5];
		// Args are in tile notation, not pixel
		bigTiles.add(new BigTile(x, y, width, height, blockSize, bigTilePath));
	}

	private void loadTP(String[] vals) {
		int x = Integer.parseInt(vals[1]);
		int y = Integer.parseInt(vals[2]);
		String location = vals[3];
		int spawnX = Integer.parseInt(vals[4]);
		int spawnY = Integer.parseInt(vals[5]);
		tpLocations.add(new TeleportData(x, y, location, spawnX, spawnY));
	}
	
	
	public void update() {
		camera.update();
		updateEntities();
		input.update();
		updatePlayer();
		updateProjectiles();
	}

	private void updateEntities() {
		for (Mob m : mobs) {
			m.move(player);
			if (m.getHealth() <= 0) {
				m.destroy();
			}
		}
		mobs.removeIf(mob -> mob.getHealth() <= 0);
	}

	public TeleportData checkStateChange() {
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				Block temp = blocks[i][j];
				if (temp.getId() == 2 && temp.getHitbox().contains(player.getX() + player.getWidth() / 2,
						player.getY() + player.getHeight() / 2)) {
					for (TeleportData t : tpLocations) {
						if (temp.getHitbox().contains(t.getX() * blockSize, t.getY() * blockSize)) {
							return t;
						}
					}
				}
			}
		}
		return null;
	}

	private void updateProjectiles() {

		for (Bullet b : player.getBullets()) {
			b.move();
		}
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		for (Bullet b : player.getBullets()) {

			if (b.checkCollide(getBlocks(), mobs)) {
				toRemove.add(b.getID());
				b.destroy();
			}
		}
		for (int i = 0; i < toRemove.size(); i++) {
			player.removeBullet(toRemove.get(i));
		}
	}

	private void updatePlayer() {
		if (InputHandler.KEYS[InputHandler.W]) {
			// camera.translate(0, cameraSpeed);
			player.updateSprite("up");
			player.move(0, cameraSpeed, getBlocks());
		}
		if (InputHandler.KEYS[InputHandler.A]) {
			// camera.translate(-cameraSpeed, 0);
			player.updateSprite("left");
			player.move(-cameraSpeed, 0, getBlocks());
		}
		if (InputHandler.KEYS[InputHandler.S]) {
			// camera.translate(0, -cameraSpeed);
			player.updateSprite("down");
			player.move(0, -cameraSpeed, getBlocks());
		}
		if (InputHandler.KEYS[InputHandler.D]) {
			// camera.translate(cameraSpeed, 0);
			player.updateSprite("right");
			player.move(cameraSpeed, 0, getBlocks());
		}

		if (InputHandler.KEYS[InputHandler.UP]) {
			player.shoot("up");
		}
		if (InputHandler.KEYS[InputHandler.LEFT]) {
			player.shoot("left");
		}
		if (InputHandler.KEYS[InputHandler.DOWN]) {
			player.shoot("down");
		}
		if (InputHandler.KEYS[InputHandler.RIGHT]) {
			player.shoot("right");
		}

		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
	}

	public void spawnMobs(int num) {
		Random r = new Random();
		for (int i = 0; i < num; i++) {
			int x = r.nextInt(width - 1) + 1;
			int y = r.nextInt(height - 1) + 1;
			mobs.add(new Mob(x * blockSize, y * blockSize, 20, 20, "entity/jelly.png", "entity/jelly_dmg.png"));
		}
	}
	
	public Block[][] getBlocks() {
		return blocks;
	}

	public boolean getLoading() {
		return loading;
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
	}

	public ArrayList<Mob> getMobs() {
		return mobs;
	}

	public ArrayList<BigTile> getBigTiles() {
		return bigTiles;
	}

	public ArrayList<TeleportData> getTpLocations() {
		return tpLocations;
	}

	public Player getPlayer() {
		return player;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
	
	
	

}
