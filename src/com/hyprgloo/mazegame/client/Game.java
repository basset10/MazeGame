package com.hyprgloo.mazegame.client;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.hyprgloo.mazegame.KC;
import com.hyprgloo.mazegame.server.LocationData;
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
			ArrayList<LocationData> coords = MainClient.getNewestInstance().getClient().getTable().<ArrayList<LocationData>>getSValue(KC.key_UIDEnemies(MainClient.getNewestInstance().getUIDK()));
			for(int i = 0; i < coords.size(); i++) {
				hvlDrawQuad(coords.get(i).loc.x, coords.get(i).loc.y, 50, 50, Color.red);
				MainClient.font.drawWordc(coords.get(i).user.name, coords.get(i).loc.x, coords.get(i).loc.y - 50, coords.get(i).user.color, 0.5f);
			}
		}

	}


}
