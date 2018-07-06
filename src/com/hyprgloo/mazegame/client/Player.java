package com.hyprgloo.mazegame.client;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public class Player {

	public static final float PLAYER_SIZE = 20;
	
	private float x;
	private float y;
	
	public Player(float xArg, float yArg) {
		
		x = xArg;
		y = yArg;
		
	}
	
	public void draw(float delta) {
		hvlDrawQuadc(x, y, PLAYER_SIZE, PLAYER_SIZE, Color.blue);
	}

	public void update(float delta) {
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			y = y - 10;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			y = y + 10;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			x = x - 10;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			x = x + 10;
		}
		
	}
	
	
	public float getY() {
		return y;
	}
	
	public void setY(float yArg) {
		y = yArg;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float xArg) {
		x = xArg;
	}
	
	
}
