package com.hyprgloo.mazegame;

import com.osreboot.hvol.base.HvlKey;

public class KC {

	private static final HvlKey KEY_USERNAME = new HvlKey("username");
	private static final HvlKey KEY_READY = new HvlKey("ready");
	private static final HvlKey KEY_COLOR = new HvlKey("color");
	private static final HvlKey KEY_ROLE = new HvlKey("role");
	private static final HvlKey KEY_LOCATION = new HvlKey("location");
	private static final HvlKey KEY_ENEMIES = new HvlKey("enemies");
	
	private static final HvlKey KEY_GAME = new HvlKey("game");
	private static final HvlKey KEY_USERLIST = new HvlKey("users");
	private static final HvlKey KEY_GAMESTATE = new HvlKey("state");
	private static final HvlKey KEY_GAMEREADYTIMER = new HvlKey("readytimer");
	
	/**
	 * @return "[0].username"
	 */
	public static HvlKey key_UIDUsername(HvlKey arg){
		return arg.with(KEY_USERNAME);
	}
	
	/**
	 * @return "[0].ready"
	 */
	public static HvlKey key_UIDReady(HvlKey arg){
		return arg.with(KEY_READY);
	}
	
	/**
	 * @return "[0].color"
	 */
	public static HvlKey key_UIDColor(HvlKey arg){
		return arg.with(KEY_COLOR);
	}
	
	/**
	 * @return "[0].role"
	 */
	public static HvlKey key_UIDRole(HvlKey arg){
		return arg.with(KEY_ROLE);
	}
	
	/**
	 * @return "[0].location"
	 */
	public static HvlKey key_UIDLocation(HvlKey arg){
		return arg.with(KEY_LOCATION);
	}
	
	/**
	 * @return "[0].enemies"
	 */
	public static HvlKey key_UIDEnemies(HvlKey arg){
		return arg.with(KEY_ENEMIES);
	}
	
	/**
	 * @return "game.users"
	 */
	public static HvlKey key_Userlist(){
		return KEY_GAME.with(KEY_USERLIST);
	}
	
	/**
	 * @return "game.state"
	 */
	public static HvlKey key_Gamestate(){
		return KEY_GAME.with(KEY_GAMESTATE);
	}
	
	/**
	 * @return "game.readytimer"
	 */
	public static HvlKey key_Gamereadytimer(){
		return KEY_GAME.with(KEY_GAMEREADYTIMER);
	}
	
}
