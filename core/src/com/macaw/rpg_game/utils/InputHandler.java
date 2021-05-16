package com.macaw.rpg_game.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler{
	private static int NUM_KEYS = 8;
	public static boolean[] KEYS = new boolean[NUM_KEYS];
	public static int W = 0;
	public static int A = 1;
	public static int S = 2;
	public static int D = 3;
	public static int UP = 4;
	public static int LEFT = 5;
	public static int DOWN = 6;
	public static int RIGHT = 7;
	
	public InputHandler() {
		for(int i = 0; i < NUM_KEYS; i++) {
			KEYS[i] = false;
		}
	}
	
	public void update() {
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			KEYS[W] = true;
		}else {
			KEYS[W] = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			KEYS[A] = true;
		}else {
			KEYS[A] = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			KEYS[S] = true;
		}else {
			KEYS[S] = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			KEYS[D] = true;
		}else {
			KEYS[D] = false;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			KEYS[UP] = true;
		}else {
			KEYS[UP] = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			KEYS[LEFT] = true;
		}else {
			KEYS[LEFT] = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			KEYS[DOWN] = true;
		}else {
			KEYS[DOWN] = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			KEYS[RIGHT] = true;
		}else {
			KEYS[RIGHT] = false;
		}
	}

}
