package com.hyprgloo.mazegame.server;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;

import com.hyprgloo.mazegame.KC;
import com.hyprgloo.mazegame.client.Menu;
import com.osreboot.hvol.base.HvlGameInfo;
import com.osreboot.hvol.base.HvlMetaServer.SocketWrapper;
import com.osreboot.hvol.dgameserver.HvlTemplateDGameServer2D;
import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeResizable;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;

public class MainServer extends HvlTemplateDGameServer2D{

	public static final boolean DEBUG = true;

	public static final int 
	INDEX_FONT = 0;

	public static final float
	VALUE_READYTIMER = 5f;

	public static enum GameState{
		LOBBY, RUNNING
	}

	public MainServer(String ipArg, int portArg, float tickRateArg, HvlGameInfo gameInfoArg) {
		super(144, 1280, 720, "MazeGame(Server) by HYPRGLOO", new HvlDisplayModeResizable(), ipArg, portArg, tickRateArg, gameInfoArg);
	}

	public static HvlFontPainter2D font;
	public static HashMap<SocketWrapper, UserData> users;

	@Override
	public void initialize(){
		getTextureLoader().loadResource("INOF");

		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_INOFFICIAL);
		font.setCharSpacing(16f);
		font.setScale(0.25f);

		users = new HashMap<>();

		Menu.initialize();

		getServer().setValue(KC.key_Userlist(), new ArrayList<>(), false);
		getServer().setValue(KC.key_Gamestate(), GameState.LOBBY, false);
		getServer().setValue(KC.key_Gamereadytimer(), VALUE_READYTIMER, false);
	}

	@Override
	public void update(float delta){
		if(DEBUG) font.drawWord(getNewestInstance().getServer().getTable().toString(), 0, 0, Color.white, 0.5f);

		boolean allReady = true;
		int userCount = 0;
		ArrayList<UserData> dataList = new ArrayList<>();
		for(SocketWrapper s : getAuthenticatedUsers()){
			userCount++;
			if(getServer().getTable().getPopulation(KC.key_UIDUsername(getUIDK(s))) > 0){
				String name = getServer().getTable().<String>getSValue(KC.key_UIDUsername(getUIDK(s)));
				boolean ready = getServer().getTable().<Boolean>getSValue(KC.key_UIDReady(getUIDK(s)));
				Color color = getServer().getTable().<Color>getSValue(KC.key_UIDColor(getUIDK(s)));
				UserData data = new UserData(name, ready, color);
				users.put(s, data);
				dataList.add(data);
				if(!ready) allReady = false;
			}
		}
		getServer().setValue(KC.key_Userlist(), dataList, false);

		if(allReady && userCount > 1 && getServer().getTable().<GameState>getSValue(KC.key_Gamestate()) == GameState.LOBBY){
			getServer().setValue(KC.key_Gamereadytimer(), HvlMath.stepTowards(getServer().getTable().<Float>getSValue(KC.key_Gamereadytimer()), delta, 0), false);
		}else{
			getServer().setValue(KC.key_Gamereadytimer(), VALUE_READYTIMER, false);
		}
		if(getServer().getTable().<Float>getSValue(KC.key_Gamereadytimer()) == 0 && 
				getServer().getTable().<GameState>getSValue(KC.key_Gamestate()) == GameState.LOBBY){
			getServer().setValue(KC.key_Gamestate(), GameState.RUNNING, false);
			getServer().setValue(KC.key_Gamereadytimer(), VALUE_READYTIMER, false);
		}
		if(getServer().getTable().<GameState>getSValue(KC.key_Gamestate()) == GameState.RUNNING){
			for(SocketWrapper s : getAuthenticatedUsers()){
				if(getServer().getTable().getPopulation(KC.key_UIDLocation(getUIDK(s))) > 1){
					ArrayList<HvlCoord2D> enemies = new ArrayList<>();
					for(SocketWrapper s2 : getAuthenticatedUsers()){
						if(s != s2){
							enemies.add(getServer().getTable().<HvlCoord2D>getSValue(KC.key_UIDLocation(getUIDK(s2))));
						}
					}
					getServer().setValue(KC.key_UIDEnemies(getUIDK(s)), enemies, false);
				}
			}
		}
	}

	@Override
	public void onConnection(SocketWrapper target){
		getServer().addMember(target, KC.key_Userlist());
		getServer().addMember(target, KC.key_Gamestate());
		getServer().addMember(target, KC.key_Gamereadytimer());
	}

	@Override
	public void onDisconnection(SocketWrapper target){
		ArrayList<UserData> dataList = new ArrayList<>(getServer().getTable().<ArrayList<UserData>>getSValue(KC.key_Userlist()));
		dataList.remove(users.get(target));
		getServer().setValue(KC.key_Userlist(), dataList, false);
	}

}
