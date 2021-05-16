package com.macaw.rpg_game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.macaw.rpg_game.GameUI;
import com.macaw.rpg_game.utils.TextureUtils;

public class MainMenuScreen implements Screen{
	
	private GameUI game;
	private Texture exitButton;
	private Texture exitActive;
	private Texture playButton;
	private Texture playActive;
	private Texture singleButton;
	private Texture singleActive;
	private Texture onlineButton;
	private Texture onlineActive;
	private Texture backButton;
	private Texture backActive;
	private float midWidth;
	private float midHeight;
	
	private BitmapFont font;
	
	private SpriteBatch renderBatch;
	
	private boolean drawSecond;
	
	public MainMenuScreen(GameUI game) {
		this.game = game;
		renderBatch = new SpriteBatch();
		String dir = TextureUtils.getInternalPath("");
		exitButton = new Texture(Gdx.files.internal(dir + "ui/exitButton.png"));
		exitActive = new Texture(Gdx.files.internal(dir + "ui/exitActive.png"));
		playButton = new Texture(Gdx.files.internal(dir + "ui/playButton.png"));
		playActive = new Texture(Gdx.files.internal(dir + "ui/playActive.png"));
		singleButton = new Texture(Gdx.files.internal(dir + "ui/singleButton.png"));
		singleActive = new Texture(Gdx.files.internal(dir + "ui/singleActive.png"));
		onlineButton = new Texture(Gdx.files.internal(dir + "ui/onlineButton.png"));
		onlineActive = new Texture(Gdx.files.internal(dir + "ui/onlineActive.png"));
		backButton = new Texture(Gdx.files.internal(dir + "ui/backButton.png"));
		backActive = new Texture(Gdx.files.internal(dir + "ui/backActive.png"));
		dir = dir.replace("ui", "");
		font = new BitmapFont(Gdx.files.internal(dir + "font1.fnt"), Gdx.files.internal(dir + "font1_transparent.png"), false);
		font.setColor(Color.GOLD);
		font.getData().scale(3);
		
		System.out.println("Loaded ui textures from: " + dir);
		midWidth = Gdx.graphics.getWidth() / 2;
		midHeight = Gdx.graphics.getHeight() / 2;
		
		drawSecond = false;
	}

	@Override
	public void show() {
		
	}
	
	private boolean containsMouse(float x, float y, float width, float height) {
		return Gdx.input.getX() < x + width && Gdx.input.getX() > x &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < y + height &&
				Gdx.graphics.getHeight() - Gdx.input.getY() > y;
	}
	
	private boolean clicked() {
		return Gdx.input.isTouched();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderBatch.begin();
		if(drawSecond) {
			drawSecondButtons();
		}else {
			drawFirstButtons();
		}
		
		
		renderBatch.end();
	}
	
	private void drawFirstButtons() {
		float middle = midWidth - playButton.getWidth() / 2;
		
		font.draw(renderBatch, "Macaw RPG", midWidth - 300, midHeight + 300);
		
		if(containsMouse(middle, midHeight, playButton.getWidth(), playButton.getHeight())) {
			renderBatch.draw(playActive, middle, midHeight);
			if(clicked()) {
				drawSecond = true;
			}
		}else {
			renderBatch.draw(playButton, middle, midHeight);
		}
		
		if(containsMouse(middle, midHeight - 200, exitButton.getWidth(), exitButton.getHeight())) {
			renderBatch.draw(exitActive, middle, midHeight - 200);
			if(clicked()) {
				Gdx.app.exit();
			}
		}else {
			renderBatch.draw(exitButton, middle, midHeight - 200);
		}
	}
	
	private void drawSecondButtons() {
		float middle = midWidth - playButton.getWidth() / 2;
		font.draw(renderBatch, "Macaw RPG", midWidth - 300, midHeight + 300);
		
		if(containsMouse(middle, midHeight - 100, singleButton.getWidth(), singleButton.getHeight())) {
			renderBatch.draw(singleActive, middle, midHeight - 100);
			if(clicked()) {
				game.setScreen(new MainGameScreen(game));
			}
		}else {
			renderBatch.draw(singleButton, middle, midHeight - 100);
		}
		
		if(containsMouse(middle, midHeight - 300, onlineButton.getWidth(), onlineButton.getHeight())) {
			renderBatch.draw(onlineActive, middle, midHeight - 300);
			if(clicked()) {
				System.out.println("Launch Online");
			}
		}else {
			renderBatch.draw(onlineButton, middle, midHeight - 300);
		}
		
		if(containsMouse(middle - 300, midHeight - 300, backButton.getWidth(), backButton.getHeight())) {
			renderBatch.draw(backActive, middle - 300, midHeight - 300);
			if(clicked()) {
				drawSecond = false;
			}
		}else {
			renderBatch.draw(backButton, middle - 300, midHeight - 300);
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		renderBatch.dispose();
		font.dispose();
		exitButton.dispose();
		exitActive.dispose();
		playButton.dispose();
		playActive.dispose();
	}
	
}
