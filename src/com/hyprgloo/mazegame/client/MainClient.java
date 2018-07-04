package com.hyprgloo.mazegame.client;

import org.newdawn.slick.Color;

import com.osreboot.hvol.base.HvlGameInfo;
import com.osreboot.hvol.dclient.HvlTemplateDClient2D;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;

public class MainClient extends HvlTemplateDClient2D{

	public static final boolean DEBUG = true;
	
	public static final int 
	INDEX_FONT = 0;
	
	public MainClient(String ipArg, int portArg, float tickRateArg, HvlGameInfo gameInfoArg) {
		super(144, 1280, 720, "MazeGame by HYPRGLOO", new HvlDisplayModeDefault(), ipArg, portArg, tickRateArg, gameInfoArg);
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
		Menu.update(delta);
		if(DEBUG) font.drawWord(getNewestInstance().getClient().getTable().toString(), 0, 0, new Color(1f, 1f, 1f, 0.2f), 0.5f);
	}
	
	@Override
	public void onConnection(){
		
	}

	@Override
	public void onDisconnection(){
		Menu.onDisconnection();
	}

}
