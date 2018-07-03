package com.hyprgloo.mazegame.client;

public class Block {

	public static final float BLOCK_SIZE = 64;
	
	private float x;
	private float y;
	
	public Block(float xArg, float yArg) {
		
		x = xArg;
		y = yArg;
		
	}
	
	public float getY() {
		return y;
	}
	
	public float getX() {
		return x;
	}
	
	
	
	
}
