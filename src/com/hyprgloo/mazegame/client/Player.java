package com.hyprgloo.mazegame.client;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlCoord2D;
import com.osreboot.ridhvl.HvlMath;

public class Player {

	public static final float PLAYER_SIZE = 20;
	public static final float ACCELERATION = 8500f;
	public static final float MAX_SPEED = 450;
	public static final float MAX_WIDTH = 1280;
	public static final float MAX_LENGTH = 720;
	
	public HvlCoord2D impartedMomentum = new HvlCoord2D();
	
	private float x;
	private float y;
	private float xSpeed;
	private float ySpeed;
	
	public Player(float xArg, float yArg) {
		
		x = xArg;
		y = yArg;
		
	}
	
	public void draw(float delta) {
		hvlDrawQuadc(x, y, PLAYER_SIZE, PLAYER_SIZE, Color.blue);
	}

	public void update(float delta) {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			
			ySpeed = ySpeed - (delta * ACCELERATION);
			
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			
			ySpeed = ySpeed + (delta * ACCELERATION);
			
		}else {
			
			ySpeed = HvlMath.stepTowards(ySpeed, ACCELERATION * delta, 0);
			
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			
			xSpeed = xSpeed - (delta * ACCELERATION);
			
		}else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			
			xSpeed = xSpeed + (delta * ACCELERATION);
			
		}else {
			
			xSpeed = HvlMath.stepTowards(xSpeed, ACCELERATION * delta, 0);
			
		}

		x = x + (xSpeed * delta);
		y = y + (ySpeed * delta);
		
		if(xSpeed >= MAX_SPEED) {
			xSpeed = MAX_SPEED;
		}
		
		if(xSpeed <= -MAX_SPEED) {
			xSpeed = -MAX_SPEED;
		}
		
		if(ySpeed >= MAX_SPEED) {
			ySpeed = MAX_SPEED;
		}
		
		if(ySpeed <= -MAX_SPEED) {
			ySpeed = -MAX_SPEED;
		}
		
		y = HvlMath.limit(y, PLAYER_SIZE/2, MAX_LENGTH - (PLAYER_SIZE/2));
		x = HvlMath.limit(x, PLAYER_SIZE/2, MAX_WIDTH - (PLAYER_SIZE/2));

		
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
