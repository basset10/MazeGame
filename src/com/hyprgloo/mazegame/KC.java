package com.hyprgloo.mazegame;

import com.osreboot.hvol.base.HvlKey;

public class KC {

	private static final HvlKey KEY_USERNAME = new HvlKey("username");
	
	private static final HvlKey KEY_GAME = new HvlKey("game");
	private static final HvlKey KEY_USERLIST = new HvlKey("users");
	
	/**
	 * @return "[0].username"
	 */
	public static HvlKey key_Username(HvlKey arg){
		return arg.with(KEY_USERNAME);
	}
	
	/**
	 * @return "game.users"
	 */
	public static HvlKey key_Userlist(){
		return KEY_GAME.with(KEY_USERLIST);
	}
	
}
