package com.macaw.rpg_game.utils;

public class Vector {

	private int x;
	private int y;
	private int data;
	
	public Vector(int x, int y, int data) {
		this.x = x;
		this.y = y;
		this.data = data;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}
	
	public boolean equals(Vector other) {
		return this.x == other.getX() && this.y == other.getY() && this.data == other.getData();
	}
	
	
}
