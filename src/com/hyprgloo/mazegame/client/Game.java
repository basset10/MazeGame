package com.hyprgloo.mazegame.client;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.hyprgloo.mazegame.KC;
import com.osreboot.ridhvl.HvlCoord2D;

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

		if(MainClient.getNewestInstance().getClient().getTable().getPopulation(KC.key_UIDEnemies(MainClient.getNewestInstance().getUIDK())) > 0){
			ArrayList<HvlCoord2D> coords = MainClient.getNewestInstance().getClient().getTable().<ArrayList<HvlCoord2D>>getSValue(KC.key_UIDEnemies(MainClient.getNewestInstance().getUIDK()));
			for(int i = 0; i < coords.size(); i++) {
				hvlDrawQuad(coords.get(i).x, coords.get(i).y, 50, 50, Color.red);
			}
		}

	}


}
