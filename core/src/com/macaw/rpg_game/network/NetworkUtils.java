package com.macaw.rpg_game.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class NetworkUtils {

	public static final int DEFULT_PORT = 1738;
	
	public static void registerEndPoint(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		//Add classes for serializing here
	}
}
