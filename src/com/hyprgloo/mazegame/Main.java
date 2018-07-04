package com.hyprgloo.mazegame;

import com.hyprgloo.mazegame.client.MainClient;
import com.hyprgloo.mazegame.server.MainServer;
import com.osreboot.hvol.base.HvlGameInfo;

@SuppressWarnings("unused")
public class Main {

	public static final String 
	INFO_GAME = "MazeGame",
	INFO_VERSION = "0.1";
	
	public static final int
	INFO_PORT = 25565;
	
	public static void main(String[] args) {
		new MainClient("localhost", INFO_PORT, 0.05f, new HvlGameInfo(INFO_GAME, INFO_VERSION, INFO_PORT));
		//new MainServer("localhost", INFO_PORT, 0.05f, new HvlGameInfo(INFO_GAME, INFO_VERSION, INFO_PORT));
	}

}
