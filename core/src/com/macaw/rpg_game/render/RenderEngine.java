package com.macaw.rpg_game.render;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.macaw.rpg_game.entity.BigTile;
import com.macaw.rpg_game.entity.Bullet;
import com.macaw.rpg_game.entity.Mob;
import com.macaw.rpg_game.entity.Player;
import com.macaw.rpg_game.ui.IngameUI;
import com.macaw.rpg_game.utils.TextureUtils;
import com.macaw.rpg_game.world.Block;
import com.macaw.rpg_game.world.World;

public class RenderEngine {
	
	private static int NUM_TEXTURES = 6;
	private static Texture[] TEXTURES = new Texture[NUM_TEXTURES];
	private static int GRASS = 0;
	private static int ROCK = 1;
	private static int TELE = 2;
	private static int WATER = 3;
	private static int BRICK = 4;
	private static int STONEPATH = 5;
	
	private static final float VIEWPORT_BUFFER = 20f;
	
	private SpriteBatch mainBatch, uiBatch;
	private World world;
	
	private IngameUI ingameUI;
	
	private BitmapFont font;
	
	public RenderEngine(World world) {
		this.world = world;
		setTextures();
		loadFonts();
		init();
	}
	
	private void init() {
		mainBatch = new SpriteBatch();
		uiBatch = new SpriteBatch();
		ingameUI = new IngameUI(world.getPlayer(), font);
	}
	
	private void setTextures() {
		String dir = TextureUtils.getInternalPath("worldTextures");
		System.out.println("Loaded world textures from: " + dir);
		TEXTURES[GRASS] = new Texture(Gdx.files.internal(dir + "/grass.png"));
		TEXTURES[ROCK] = new Texture(Gdx.files.internal(dir + "/rock.png"));
		TEXTURES[TELE] = new Texture(Gdx.files.internal(dir + "/tele.png"));
		TEXTURES[WATER] = new Texture(Gdx.files.internal(dir + "/water1.png"));
		TEXTURES[BRICK] = new Texture(Gdx.files.internal(dir + "/brick.png"));
		TEXTURES[STONEPATH] = new Texture(Gdx.files.internal(dir + "/stonePath.png"));
	}
	
	private void loadFonts() {
		String dir = TextureUtils.getInternalPath("");
		System.out.println("Loaded fonts from: " + dir);
		font = new BitmapFont(Gdx.files.internal(dir + "font1.fnt"), Gdx.files.internal(dir + "font1_transparent.png"), false);
	}
	
	public void changeState(World world) {
		this.world = world;
	}
	
	public void update() {
		mainBatch.setProjectionMatrix(world.getCamera().combined);
	}
	
	public void render() {
		mainBatch.begin();
		renderWorld(mainBatch);
		renderProjectiles(mainBatch);
		renderPlayer(mainBatch);
		renderMobs(mainBatch);
		mainBatch.end();
		
		uiBatch.begin();
		renderUI(uiBatch);
		uiBatch.end();
	}
	
	private void renderUI(SpriteBatch batch) {
		ingameUI.drawBars(batch);
	}
	
	public void renderLoadingScreen() {
		font.setColor(Color.GOLD);
		font.getData().scale(2);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		uiBatch.begin();
		font.draw(uiBatch, "Loading...", 100, 100);
		uiBatch.end();
	}
	
	private void renderWorld(SpriteBatch batch) {
		for (int j = 0; j < world.getHeight(); j++) {
			for (int i = 0; i < world.getWidth(); i++) {
				Block temp = world.getBlocks()[j][i];
				if (inViewport(temp, VIEWPORT_BUFFER)) {
					Texture tex = TEXTURES[temp.getId()];
					batch.draw(tex, temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
				}

			}

		}

		for (BigTile b : world.getBigTiles()) {
			b.render(batch);
		}
	}
	
	private boolean inViewport(Block b, float buffer) {
		OrthographicCamera camera = world.getCamera();
		float xMin = camera.position.x - (camera.viewportWidth / 2);
		float yMin = camera.position.y - (camera.viewportHeight / 2);
		float xMax = camera.position.x + (camera.viewportWidth / 2);
		float yMax = camera.position.y + (camera.viewportHeight / 2);

		return b.getX() > xMin - buffer && b.getX() < xMax + buffer && b.getY() > yMin - buffer
				&& b.getY() < yMax + buffer;
	}
	
	public void renderPlayer(SpriteBatch batch) {
		world.getPlayer().render(batch);
	}

	public void renderMobs(SpriteBatch batch) {
		for (Mob m : world.getMobs()) {
			m.render(batch);
		}
	}

	public void renderProjectiles(SpriteBatch batch) {
		for (Bullet b : world.getPlayer().getBullets()) {
			b.render(batch);
		}
	}

	

}
