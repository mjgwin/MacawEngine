package com.macaw.rpg_game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/*
 * @author Micah Gwin
 * 
 * This class is a simple utility class containing methods for
 * loading both individual textures and sprite sheets. It also
 * gets the internal path to the executable which is important
 * for shipping the game in a jar.
 */

public class TextureUtils {
	
	public static TextureRegion[][] loadSpriteSheet(String path, int rows, int cols){
		String dir = TextureUtils.getInternalPath(path);
		System.out.println("Loaded sprite sheet from: " + dir);
		Texture sheet = new Texture(Gdx.files.internal(dir));
		TextureRegion[][] temp = TextureRegion.split(sheet, sheet.getWidth() / rows, sheet.getHeight() / cols);
		return temp;
	}
	
	public static String getInternalPath(String assetPath) {
		String directory = System.getProperty("user.dir");
		
		//this is for external use (gradle)
		//directory = directory.concat("/assets/" + assetPath);
		
		//this is for eclipse internal use
		directory = directory.replace("desktop", "core");
		directory = directory.concat("/assets/" + assetPath);
		return directory;
	}

}
