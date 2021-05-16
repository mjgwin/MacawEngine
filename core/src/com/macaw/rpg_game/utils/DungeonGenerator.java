package com.macaw.rpg_game.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.math.Vector2;
import com.macaw.rpg_game.world.Block;
import com.macaw.rpg_game.world.Room;

public class DungeonGenerator {
	
	private static final int MAX_ROOMS = 10;
	private static final int MIN_ROOMS = 5;
	private static final int MAX_ROOM_SIZE = 20;
	private static final int MIN_ROOM_SIZE = 10;
	private static final int ROOM_SPACE_BUFFER = 5;
	
	private int width, height;
	private float blockSize;
	private Block[][] map;
	private Vector[][] world;
	private ArrayList<Room> rooms;
	private ArrayList<Vector> hasPath;
	public DungeonGenerator(int width, int height, float blockSize, int roomNum) {
		this.width = width;
		this.height = height;
		this.blockSize = blockSize;
		map = new Block[height][width];
		world = new Vector[height][width];
		rooms = new ArrayList<Room>();
		hasPath = new ArrayList<Vector>();
		if(roomNum == 0) {
			int randRooms = ThreadLocalRandom.current().nextInt(MIN_ROOMS, MAX_ROOMS + 1);
			addRooms(randRooms);
		}else {
			addRooms(roomNum);
		}
		
		setMap();
		generateHallways();
	}
	
	private void addRooms(int num) {
		int count = 0;
		int attempts = 0;
		while(count < num) {
			int sizeX = ThreadLocalRandom.current().nextInt(MIN_ROOM_SIZE, MAX_ROOM_SIZE + 1);
			int sizeY = ThreadLocalRandom.current().nextInt(MIN_ROOM_SIZE, MAX_ROOM_SIZE + 1);
			int posX =  ThreadLocalRandom.current().nextInt(1, width - sizeX);
			int posY =  ThreadLocalRandom.current().nextInt(1, height - sizeY);
			if(!roomsIntersect(posX, posY, sizeX, sizeY, ROOM_SPACE_BUFFER)) {
				rooms.add(new Room(posX, posY, sizeX, sizeY));
				count++;
			}
			if(attempts == 100000) {
				break;
			}
			attempts++;
		}
		
		System.out.println("Supposed to add: " + num + ", actually added: " + rooms.size());
	}
	
	
	
	private boolean roomsIntersect(int x, int y, int width, int height, int buffer) {
		for(Room r : rooms) {
			if(x < r.getX() + r.getWidth() + buffer && x + width + buffer > r.getX() && y < r.getY() + r.getHeight() + buffer && y + height + buffer> r.getY()) {
				return true;
			}
			
		}
		return false;
	}
	
	private void setMap() {
		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {
				if(roomsIntersect(i, j, 1, 1, 0)) {
					map[j][i] = new Block(i * blockSize, j * blockSize, blockSize, blockSize, 5);
					world[j][i] = new Vector(i, j, 1);
				}else {
					map[j][i] = new Block(i * blockSize, j * blockSize, blockSize, blockSize, 1);
					world[j][i] = new Vector(i, j, 0);
				}
			}
		}
	}
	
	public Vector2 getRandomPlayerSpawn() {
		Vector2 toReturn = new Vector2();
		int roomNum = ThreadLocalRandom.current().nextInt(0, rooms.size());
		int spawnX = rooms.get(roomNum).getX() + rooms.get(roomNum).getWidth() / 2;
		int spawnY = rooms.get(roomNum).getY() + rooms.get(roomNum).getHeight() / 2;
		toReturn.x = spawnX * blockSize;
		toReturn.y = spawnY * blockSize;
		//System.out.println("Spawn: " + toReturn.x + " , " + toReturn.y + " in room: " + roomNum);
		//printRooms();
		return toReturn;
	}
	
	private void printRooms() {
		int count = 0;
		for(Room r : rooms) {
			System.out.println("Room " + count + ": x: " + r.getX() * blockSize + " y: " + r.getY() * blockSize + " w: " + r.getWidth() * blockSize + " h: " + r.getHeight() * blockSize);
			count++;
		}
	}
	
	private void generateHallways() {
		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {
				for(Room r : rooms) {
					if(j == r.getY() + r.getHeight() / 2 && i == r.getX() + r.getWidth() / 2) {
						world[j][i] = new Vector(i, j, 2);
					}
				}
			}
		}
		
		for(Room r : rooms) {
			ArrayList<Vector> path = pathToNearestRoom(r);
			for(Vector vec : path) {
				map[vec.getY() - 1][vec.getX() - 1].setId(5);
				map[vec.getY()][vec.getX()].setId(5);
				map[vec.getY() + 1][vec.getX() + 1].setId(5);
			}
		}
	}
	
	private ArrayList<Vector> pathToNearestRoom(Room start) {
		boolean[][] visited = new boolean[height][width];
		Vector[][] prev = new Vector[height][width];
		Vector dest = null;
		Vector startVec = null;
		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {
				visited[j][i] = false;
			}
		}
		LinkedList<Vector> queue = new LinkedList<Vector>();
		ArrayList<Vector> path = new ArrayList<Vector>();
		int startX = start.getX() + start.getWidth() / 2;
		int startY = start.getY() + start.getHeight() / 2;
		visited[startX][startY] = true;
		startVec = world[startY][startX];
		queue.add(startVec);
		
		while(!queue.isEmpty()) {
			Vector temp = queue.poll();
			
			int x = temp.getX();
			int y = temp.getY();
			
			if(world[y][x].getData() == 2 && !alreadyConnected(world[y][x]) && x != startX && y != startY) {
				dest = new Vector(x, y, 2);
				hasPath.add(dest);
				break;
			}
			
			if(inBounds(x - 1, y)) {
				if(!visited[y][x-1]) {
					visited[y][x-1] = true;
					prev[y][x-1] = temp;
					queue.add(world[y][x-1]);
				}
			}
			
			if(inBounds(x, y - 1)) {
				if(!visited[y-1][x]) {
					visited[y-1][x] = true;
					prev[y-1][x] = temp;
					queue.add(world[y-1][x]);
				}
			}
			
			if(inBounds(x, y + 1)) {
				if(!visited[y+1][x]) {
					visited[y+1][x] = true;
					prev[y+1][x] = temp;
					queue.add(world[y+1][x]);
				}
			}
			
			if(inBounds(x + 1, y)) {
				if(!visited[y][x+1]) {
					visited[y][x+1] = true;
					prev[y][x+1] = temp;
					queue.add(world[y][x+1]);
				}
			}
			
		}
		
		if(dest == null) {
			System.out.println("Pathfinding error");
			return null;
		}
		
		path.add(dest);
		Vector temp = dest;
		
		while(prev[temp.getY()][temp.getX()].getData() != 2) {
			path.add(prev[temp.getY()][temp.getX()]);
			temp = prev[temp.getY()][temp.getX()];
		}
		path.add(startVec);
		return path;
	}
	
	private boolean alreadyConnected(Vector v) {
		if(hasPath.isEmpty()) return false;
		
		for(Vector vec : hasPath) {
			if(vec.equals(v)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean inBounds(int x, int y) {
		return x > -1 && x < width && y > -1 && y < height;
	}
	
	public Block[][] getMap(){
		return map;
	}
}
