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
	
	public HvlCoord2D pos;
	public HvlCoord2D speed;
	
	public Player(float xArg, float yArg) {
		
		pos = new HvlCoord2D(500, 500);
		speed = new HvlCoord2D();
		
	}
	
	public void draw(float delta) {
		hvlDrawQuadc(pos.x, pos.y, PLAYER_SIZE, PLAYER_SIZE, Color.blue);
	}

	public void update(float delta) {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			
			speed.y = speed.y - (delta * ACCELERATION);
			
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			
			speed.y = speed.y + (delta * ACCELERATION);
			
		}else {
			
			speed.y = HvlMath.stepTowards(speed.y, ACCELERATION * delta, 0);
			
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			
			speed.x = speed.x - (delta * ACCELERATION);
			
		}else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			
			speed.x = speed.x + (delta * ACCELERATION);
			
		}else {
			
			speed.x = HvlMath.stepTowards(speed.x, ACCELERATION * delta, 0);
			
		}

		pos.x = pos.x + (speed.x * delta);
		pos.y = pos.y + (speed.y * delta);
		
		if(speed.x >= MAX_SPEED) {
			speed.x = MAX_SPEED;
		}
		
		if(speed.x <= -MAX_SPEED) {
			speed.x = -MAX_SPEED;
		}
		
		if(speed.y >= MAX_SPEED) {
			speed.y = MAX_SPEED;
		}
		
		if(speed.y <= -MAX_SPEED) {
			speed.y = -MAX_SPEED;
		}
		
		pos.y = HvlMath.limit(pos.y, PLAYER_SIZE/2, MAX_LENGTH - (PLAYER_SIZE/2));
		pos.x = HvlMath.limit(pos.x, PLAYER_SIZE/2, MAX_WIDTH - (PLAYER_SIZE/2));

		
	}
	
}
