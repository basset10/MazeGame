package com.hyprgloo.mazegame.client;

public class Game {
	
	public Player player;

	public Game() {
		player = new Player(500, 500);
		player.draw(delta);
		
	}

	
}
