package com.hyprgloo.mazegame.server;

import java.util.ArrayList;

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

	@Override
	public void initialize(){
		getTextureLoader().loadResource("INOF");

		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_INOFFICIAL);
		font.setCharSpacing(16f);
		font.setScale(0.25f);

		Menu.initialize();

		getServer().setValue(KC.key_Gamestate(), GameState.LOBBY, false);
		getServer().setValue(KC.key_Gamereadytimer(), VALUE_READYTIMER, false);
	}

	@Override
	public void update(float delta){
		if(DEBUG) font.drawWord(getServer().getTable().toString(), 0, 0, Color.white, 0.5f);

		boolean allReady = true;
		int userCount = 0;
		for(SocketWrapper s : getAuthenticatedUsers()){
			userCount++;
			if(getServer().hasValue(KC.key_UIDUsername(getUIDK(s)))){
				if(!getServer().<Boolean>getValue(KC.key_UIDReady(getUIDK(s)))) allReady = false;
			}
		}

		if(allReady && userCount > 1 && getServer().<GameState>getValue(KC.key_Gamestate()) == GameState.LOBBY){
			getServer().setValue(KC.key_Gamereadytimer(), HvlMath.stepTowards(getServer().<Float>getValue(KC.key_Gamereadytimer()), delta, 0), false);
		}else{
			getServer().setValue(KC.key_Gamereadytimer(), VALUE_READYTIMER, false);
		}
		if(getServer().<Float>getValue(KC.key_Gamereadytimer()) == 0 && 
				getServer().<GameState>getValue(KC.key_Gamestate()) == GameState.LOBBY){
			getServer().setValue(KC.key_Gamestate(), GameState.RUNNING, false);
			getServer().setValue(KC.key_Gamereadytimer(), VALUE_READYTIMER, false);
		}
		if(getServer().<GameState>getValue(KC.key_Gamestate()) == GameState.RUNNING){
			for(SocketWrapper s : getAuthenticatedUsers()){
				if(getServer().hasValue(KC.key_UIDLocation(getUIDK(s)))){
					ArrayList<HvlCoord2D> enemies = new ArrayList<>();
					for(SocketWrapper s2 : getAuthenticatedUsers()){
						if(s != s2 && getServer().hasValue(KC.key_UIDLocation(getUIDK(s2)))){
							enemies.add(getServer().<HvlCoord2D>getValue(KC.key_UIDLocation(getUIDK(s2))));
						}
					}
					getServer().setValue(KC.key_UIDEnemies(getUIDK(s)), enemies, false);
				}
			}
		}
	}

	@Override
	public void onConnection(SocketWrapper target){
		getServer().addMember(target, KC.key_Gamestate());
		getServer().addMember(target, KC.key_Gamereadytimer());
		for(SocketWrapper s : getAuthenticatedUsers()){
			if(target != s){
				getServer().addMember(target, KC.key_UIDInfo(getUIDK(s)));
				getServer().addMember(s, KC.key_UIDInfo(getUIDK(target)));
			}
		}
	}

	@Override
	public void onDisconnection(SocketWrapper target){
		
	}

}
