package com.hyprgloo.mazegame.client;

import java.util.ArrayList;

import com.hyprgloo.mazegame.KC;

public class Game {
	
	public Player player;
	public ArrayList<Block> blocks;

	public Game() {
		player = new Player(500, 500);
	}
	
	public void update(float delta) {
		player.update(delta);
		player.draw(delta);
		MainClient.getNewestInstance().getClient().setValue(KC.key_UIDLocation(MainClient.getNewestInstance().getUIDK()), player.pos, false);
		
	}

	
}
