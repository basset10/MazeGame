package com.hyprgloo.mazegame.server;

import java.io.Serializable;

import com.osreboot.ridhvl.HvlCoord2D;

public class LocationData implements Serializable{
	private static final long serialVersionUID = -3958933311005733318L;
	
	public HvlCoord2D loc;
	public UserData user;
	
	public LocationData(HvlCoord2D locArg, UserData userArg){
		loc = locArg;
		user = userArg;
	}

}
