package com.macaw.rpg_game.utils;

public class TeleportData {

	private int x,y;
	private String location;
	private int spawnX, spawnY;
	
	public TeleportData(int x, int y, String location, int spawnX, int spawnY) {
		this.x = x;
		this.y = y;
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.location = location;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}



	public int getSpawnX() {
		return spawnX;
	}



	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}



	public int getSpawnY() {
		return spawnY;
	}



	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}
	
	


	
	
}
