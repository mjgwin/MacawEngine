package com.macaw.rpg_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.macaw.rpg_game.GameUI;
import com.macaw.rpg_game.render.RenderEngine;
import com.macaw.rpg_game.utils.EngineLogger;
import com.macaw.rpg_game.utils.TeleportData;
import com.macaw.rpg_game.world.World;

/*
 *  @author Micah Gwin
 *  
 * Manages world creation and render/Update calls
 * If multiplayer is implemented, this renderer would become the client
 */

public class MainGameScreen implements Screen {

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

	/*
	 * Sets up world and camera, if world is loaded from file then width and height
	 * below are stored in the file not here. Can also control player and mob spawns
	 * if needed. Lastly starts the rendering engine and passes the created world
	 */

	private void initGame() {
		worldWidth = 90;
		worldHeight = 90;
		blockSize = 10;
		cameraSpeed = 0.5f;
		cameraScale = 2;

		try {
			camera = new OrthographicCamera(worldWidth * cameraScale, worldHeight * cameraScale);
			EngineLogger.log("Camera loaded");
		} catch (Exception e) {
			EngineLogger.log("CRITICAL ERROR: FAILED TO LOAD CAMERA");
			e.printStackTrace();
		}

		//world = new World(100, 100, 10, camera, cameraSpeed);
		//world = new World("worldMaps/spawn.txt", camera, cameraSpeed);
		//world.spawnPlayer(14,15);
		//world.spawnMobs(10);
		

		try {
			world = new World("worldMaps/testArena.txt", camera, cameraSpeed);
			world.spawnPlayer(45,45);
			world.spawnAnimatedMobs(20);
			EngineLogger.log("World loaded");
		} catch (Exception e) {
			EngineLogger.log("CRITICAL ERROR: FAILED TO LOAD WORLD");
			e.printStackTrace();
		}

		try {
			renderEngine = new RenderEngine(world);
			EngineLogger.log("Render Engine started");
		} catch (Exception e) {
			EngineLogger.log("CRITICAL ERROR: FAILED TO START RENDER ENGINE");
			e.printStackTrace();
		}

	}

	/*
	 * Rendering loop that also updates the world and checks for state changes using
	 * the TeleportData class. If a state change is sent, the loading screen is
	 * displayed and a new world is created from the given path
	 */

	@Override
	public void render(float delta) {

		try {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			world.update();
			renderEngine.update();

			TeleportData t = world.checkStateChange();
			if (t != null) {
				renderEngine.renderLoadingScreen();
				System.out.println("Changing state...");
				world = world.changeState("worldMaps/" + t.getLocation(), camera, cameraSpeed);
				world.spawnPlayer(t.getSpawnX(), t.getSpawnY());
				renderEngine.changeState(world);
			} else {
				displayGame();
			}

		} catch (Exception e) {
			EngineLogger.log("CRITICAL ERROR: RENDER LOOP IN MAINGAMESCREEN BROKEN");
			e.printStackTrace();
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
