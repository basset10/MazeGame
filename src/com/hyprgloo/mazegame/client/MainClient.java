package com.hyprgloo.mazegame.client;

import com.osreboot.hvol.base.HvlGameInfo;
import com.osreboot.hvol.dclient.HvlTemplateDClient2D;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;

public class MainClient extends HvlTemplateDClient2D{

	public MainClient(String ipArg, int portArg, float tickRateArg, HvlGameInfo gameInfoArg) {
		super(144, 1280, 720, "MazeGame by HYPRGLOO", new HvlDisplayModeDefault(), ipArg, portArg, tickRateArg, gameInfoArg);
	}

	@Override
	public void initialize(){
		
	}

	@Override
	public void update(float deltaArg){
		
	}

}
