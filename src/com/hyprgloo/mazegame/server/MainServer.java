package com.hyprgloo.mazegame.server;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;

import com.hyprgloo.mazegame.KC;
import com.hyprgloo.mazegame.client.Menu;
import com.osreboot.hvol.base.HvlGameInfo;
import com.osreboot.hvol.base.HvlMetaServer.SocketWrapper;
import com.osreboot.hvol.dgameserver.HvlTemplateDGameServer2D;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeResizable;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;

public class MainServer extends HvlTemplateDGameServer2D{

public static final boolean DEBUG = true;
	
	public static final int 
	INDEX_FONT = 0;
	
	public MainServer(String ipArg, int portArg, float tickRateArg, HvlGameInfo gameInfoArg) {
		super(144, 1280, 720, "MazeGame(Server) by HYPRGLOO", new HvlDisplayModeResizable(), ipArg, portArg, tickRateArg, gameInfoArg);
	}

	public static HvlFontPainter2D font;
	public static HashMap<SocketWrapper, String> usernames;
	
	@Override
	public void initialize(){
		getTextureLoader().loadResource("INOF");
		
		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_INOFFICIAL);
		font.setCharSpacing(16f);
		font.setScale(0.25f);
		
		usernames = new HashMap<>();
		
		Menu.initialize();
		
		getServer().setValue(KC.key_Userlist(), new ArrayList<>(), false);
	}

	@Override
	public void update(float delta){
		if(DEBUG) font.drawWord(getNewestInstance().getServer().getTable().toString(), 0, 0, Color.white);
		
		for(SocketWrapper s : getAuthenticatedUsers()){
			if(!usernames.containsKey(s) && 
					getServer().getTable().getPopulation(KC.key_Username(getUIDK(s))) > 0){
				String name = getServer().getTable().<String>getSValue(KC.key_Username(getUIDK(s)));
				usernames.put(s, name);
				ArrayList<String> users = new ArrayList<>(getServer().getTable().<ArrayList<String>>getSValue(KC.key_Userlist()));
				users.add(name);
				getServer().setValue(KC.key_Userlist(), users, false);
			}
		}
	}

	@Override
	public void onConnection(SocketWrapper target){
		getServer().addMember(target, KC.key_Userlist());
	}

	@Override
	public void onDisconnection(SocketWrapper target){
		ArrayList<String> users = new ArrayList<>(getServer().getTable().<ArrayList<String>>getSValue(KC.key_Userlist()));
		users.remove(usernames.get(target));
		getServer().setValue(KC.key_Userlist(), users, false);
	}
	
}
