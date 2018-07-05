package com.hyprgloo.mazegame.server;

import java.io.Serializable;

import org.newdawn.slick.Color;

public class UserData implements Serializable{
	private static final long serialVersionUID = -2124692160654036171L;
	
	public String name;
	public boolean ready;
	public Color color;
	
	public UserData(String nameArg, boolean readyArg, Color colorArg){
		name = nameArg;
		ready = readyArg;
		color = colorArg;
	}
	
}
