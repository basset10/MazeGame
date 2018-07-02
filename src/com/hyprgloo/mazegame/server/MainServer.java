package com.hyprgloo.mazegame.server;

import org.newdawn.slick.Color;

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
	
	@Override
	public void initialize(){
		getTextureLoader().loadResource("INOF");
		
		font = new HvlFontPainter2D(getTexture(INDEX_FONT), HvlFontPainter2D.Preset.FP_INOFFICIAL);
		font.setCharSpacing(16f);
		font.setScale(0.25f);
		
		Menu.initialize();
	}

	@Override
	public void update(float delta){
		if(DEBUG) font.drawWord(getNewestInstance().getServer().getTable().toString(), 0, 0, Color.white);
	}

	@Override
	public void onConnection(SocketWrapper target){
		
	}

	@Override
	public void onDisconnection(SocketWrapper target){
		
	}
	
}
