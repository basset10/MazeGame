package com.hyprgloo.mazegame.client;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox;
import com.osreboot.ridhvl.menu.component.HvlButton;
import com.osreboot.ridhvl.menu.component.HvlComponentDrawable;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;

public class Menu {

	public static HvlMenu main;

	public static void initialize(){
		HvlComponentDefault.setDefault(new HvlArrangerBox(Display.getWidth(), Display.getHeight(), HvlArrangerBox.ArrangementStyle.VERTICAL));
		HvlComponentDefault.setDefault(new HvlLabeledButton(256, 64, new HvlComponentDrawable() {
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg) {
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, new Color(0.15f, 0.15f, 0.15f));
			}
		}, new HvlComponentDrawable() {
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg) {
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, new Color(0.2f, 0.2f, 0.2f));
			}
		}, new HvlComponentDrawable() {
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg) {
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, new Color(0.4f, 0.4f, 0.4f));
			}
		}, MainClient.font, "NOTEXT", Color.white));

		main = new HvlMenu();
		main.add(new HvlArrangerBox.Builder().build());
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("connect").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				try{
					if(!MainClient.getNewestInstance().getClient().isConnected()){
						MainClient.getNewestInstance().getClient().start();
					}
					if(MainClient.getNewestInstance().getClient().isConnected()){
						aArg.setEnabled(false);
					}
				}catch(Exception e){

				}
			}
		}).build());

		HvlMenu.setCurrent(main);
	}

	public static void update(float delta){
		HvlMenu.updateMenus(delta);
	}
	
	public static void onDisconnection(){
		main.getFirstArrangerBox().getFirstOfType(HvlLabeledButton.class).setEnabled(true);
	}

}
