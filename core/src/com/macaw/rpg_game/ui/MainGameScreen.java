package com.macaw.rpg_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.macaw.rpg_game.GameUI;
import com.macaw.rpg_game.render.RenderEngine;
import com.macaw.rpg_game.utils.TeleportData;
import com.macaw.rpg_game.world.World;

public class MainGameScreen implements Screen{
	
	private GameUI game;
	
	private World world;
	private OrthographicCamera camera;
	private float worldWidth, worldHeight;
	private float blockSize;
	private float cameraSpeed;
	private float cameraScale;
	
	private RenderEngine renderEngine;

	public MainGameScreen(GameUI game) {
		this.game = game;
		initGame();
	}

	private void initGame() {
		worldWidth = 30;
		worldHeight = 30;
		blockSize = 10;
		cameraSpeed = 0.5f;
		cameraScale = 5;

		camera = new OrthographicCamera(worldWidth * cameraScale, worldHeight * cameraScale);
		//world = new World("worldMaps/spawn.txt", camera, cameraSpeed);
		//world.spawnPlayer(14,15);
		//world.spawnMobs(10);
		world = new World(100, 100, 10, camera, cameraSpeed);
		renderEngine = new RenderEngine(world);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.update();
		renderEngine.update();
		
		
		TeleportData t  = world.checkStateChange();
		if(t != null) {
			renderEngine.renderLoadingScreen();
			System.out.println("Changing state...");
			world = world.changeState("worldMaps/" + t.getLocation(), camera, cameraSpeed);
			world.spawnPlayer(t.getSpawnX(),t.getSpawnY());
			renderEngine.changeState(world);
		}else {
			displayGame();
		}
		
	}
	
	private void displayGame() {
		renderEngine.render();
	}

	// float xMin = 0, yMin = 0;
	// float xMax = worldWidth * blockSize;
	// float yMax = worldHeight * blockSize;
	// float maxCamX = camera.position.x + (camera.viewportWidth / 2);
	// float minCamX = camera.position.x - (camera.viewportWidth / 2);
	// float maxCamY = camera.position.y + (camera.viewportHeight / 2);
	// float minCamY = camera.position.y - (camera.viewportHeight / 2);

	@Override
	public void dispose() {

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
