package com.macaw.rpg_game.utils;

import java.io.BufferedWriter;

/*
 * @author Micah Gwin
 * 
 *  A simple logging system for MacawEngine. Prints to the
 *  terminal and a log file if specified.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class EngineLogger {
	
	
	private static boolean sendToFile = false;
	
	private static FileWriter fileWriter;
	private static BufferedWriter bufferedWriter;
	private static PrintWriter printWriter;
	
	public static void logFileSetup(String path) {
		sendToFile = true;
		
		String dir = TextureUtils.getInternalPath(path);
		
		try {
			fileWriter = new FileWriter(dir, false);
			bufferedWriter = new BufferedWriter(fileWriter);
			printWriter = new PrintWriter(bufferedWriter);
			System.out.println("<MacawEngine> Logging to file enabled, source: " + dir);
		} catch (Exception e) {
			System.out.println("<MacawEngine> Failed to setup log file, check path");
			e.printStackTrace();
		}
	}
	
	public static void log(String message) {
		System.out.println("<MacawEngine> " + message);
		if(sendToFile) {
			printWriter.println("<MacawEngine> " + message);
			printWriter.flush();
		}
	}
	
	public static void disableLogFile() {
		System.out.println("<MacawEngine> Logging to file disabled");
		sendToFile = false;
	}
	

}
