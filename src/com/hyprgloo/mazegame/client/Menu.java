package com.hyprgloo.mazegame.client;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.hyprgloo.mazegame.KC;
import com.hyprgloo.mazegame.server.MainServer.GameState;
import com.osreboot.hvol.base.HvlConnectorClient;
import com.osreboot.hvol.base.HvlKey;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox;
import com.osreboot.ridhvl.menu.component.HvlButton;
import com.osreboot.ridhvl.menu.component.HvlComponentDrawable;
import com.osreboot.ridhvl.menu.component.HvlLabel;
import com.osreboot.ridhvl.menu.component.HvlSpacer;
import com.osreboot.ridhvl.menu.component.HvlTextBox;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;

public class Menu {

	public static HvlMenu main, lobby, game;
	public static String username = "GenericUsername";
	public static Boolean ready = false;
	public static Game g;

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
		HvlTextBox defaultTextBox = new HvlTextBox(512, 48, "NOTEXT", new HvlComponentDrawable() {
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg) {
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, new Color(0.15f, 0.15f, 0.15f));
			}
		}, new HvlComponentDrawable() {
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg) {
				hvlDrawQuad(xArg, yArg, widthArg, heightArg, new Color(0.2f, 0.2f, 0.2f));
			}
		}, MainClient.font);
		defaultTextBox.setMaxCharacters(20);
		defaultTextBox.setOffsetX(8f);
		defaultTextBox.setOffsetY(8f);
		defaultTextBox.setTextColor(Color.gray);
		HvlComponentDefault.setDefault(defaultTextBox);

		main = new HvlMenu();
		main.add(new HvlArrangerBox.Builder().build());
		main.getFirstArrangerBox().add(new HvlLabel(MainClient.font, "Display name:", 0.5f, Color.white));
		main.getFirstArrangerBox().add(new HvlTextBox.Builder().setText(username).build());
		main.getFirstArrangerBox().add(new HvlSpacer(0, 16));
		main.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("connect").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				try{	
					if(!MainClient.getNClient().isConnected()){
						MainClient.getNClient().start();
					}
					if(MainClient.getNClient().isConnected()){
						aArg.setEnabled(false);
					}
				}catch(Exception e){

				}
			}
		}).build());

		lobby = new HvlMenu();
		lobby.add(new HvlArrangerBox.Builder().build());
		lobby.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("ready").setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton aArg){
				ready = !ready;
			}
		}).build());

		game = new HvlMenu();

		HvlMenu.setCurrent(main);
	}

	public static void update(float delta){
		HvlConnectorClient client = MainClient.getNClient();
		main.getFirstArrangerBox().getFirstOfType(HvlLabeledButton.class).setEnabled(
				main.getFirstArrangerBox().getFirstOfType(HvlTextBox.class).getText().length() > 4);
		username = main.getFirstArrangerBox().getFirstOfType(HvlTextBox.class).getText();
		if(HvlMenu.getCurrent() == main){
			if(MainClient.getNewestInstance().isAuthenticated()){
				ready = false;
				client.setValue(KC.key_UIDUsername(MainClient.getNUIDK()), username, false);
				client.setValue(KC.key_UIDColor(MainClient.getNUIDK()), username.equals("os_reboot") ? Color.blue : Color.white, false);
				client.setValue(KC.key_UIDReady(MainClient.getNUIDK()), false, false);
				HvlMenu.setCurrent(lobby);
			}
		}else if(HvlMenu.getCurrent() == lobby){
			MainClient.font.drawWord("Users:", 16, 256, Color.white);
			float line = 0;
			client.setValue(KC.key_UIDReady(MainClient.getNUIDK()), ready, false);
			client.setValue(KC.key_UIDColor(MainClient.getNUIDK()), 
					username.equalsIgnoreCase("os_reboot") ? new Color(0f, 0f, HvlMath.map((float)Math.sin(5f * MainClient.getNewestInstance().getTimer().getTotalTime()), -1f, 1f, 0.5f, 1f)) : Color.orange, 
							false);
			for(HvlKey k : client.getTable().getSKeys()){
				try{
					Integer.parseInt(k.toString());
					HvlKey uid = new HvlKey(k.getElements()[0]);
					MainClient.font.drawWord("[" + (client.<Boolean>getValue(KC.key_UIDReady(uid)) ? "X" : " ") + "] " + client.<String>getValue(KC.key_UIDUsername(uid)), 16, 256 + (++line * 48), client.<Color>getValue(KC.key_UIDColor(uid)));
				}catch(Exception e){}
				
			}
			if(client.<GameState>getValue(KC.key_Gamestate()) == GameState.RUNNING){
				HvlMenu.setCurrent(game);
				ready = false;
			}
		}else if(HvlMenu.getCurrent() == game){
			if(g == null) g = new Game();
			g.update(delta);
		}
		HvlMenu.updateMenus(delta);
	}

	public static void onDisconnection(){
		HvlMenu.setCurrent(main);
		g = null;
		main.getFirstArrangerBox().getFirstOfType(HvlLabeledButton.class).setEnabled(true);
	}

}
