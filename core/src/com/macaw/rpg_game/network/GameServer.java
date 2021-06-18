package com.macaw.rpg_game.network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class GameServer {
	
	private Server server;
	
	public GameServer() {
		server = new Server();
		NetworkUtils.registerEndPoint(server);
		
		server.addListener(new Listener() {
			
			public void received (Connection c, Object object) {
				
			}
			
			public void disconnected (Connection c) {
				
			}
			
		});
		
		try {
			server.bind(NetworkUtils.DEFULT_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		server.start();
	}
	
}
