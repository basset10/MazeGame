package com.hyprgloo.mazegame.client;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuad;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.hyprgloo.mazegame.KC;
import com.osreboot.hvol.base.HvlConnectorClient;
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

	public static HvlMenu main, game;
	public static String username = "GenericUsername";

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

		game = new HvlMenu();
		game.add(new HvlArrangerBox.Builder().build());

		HvlMenu.setCurrent(main);
	}

	public static void update(float delta){
		HvlConnectorClient client = MainClient.getNewestInstance().getClient();
		main.getFirstArrangerBox().getFirstOfType(HvlLabeledButton.class).setEnabled(
				main.getFirstArrangerBox().getFirstOfType(HvlTextBox.class).getText().length() > 4);
		username = main.getFirstArrangerBox().getFirstOfType(HvlTextBox.class).getText();
		if(HvlMenu.getCurrent() == main){
			if(MainClient.getNewestInstance().isAuthenticated()){
				client.setValue(KC.key_Username(MainClient.getNewestInstance().getUIDK()), main.getFirstArrangerBox().getFirstOfType(HvlTextBox.class).getText(), false);
				HvlMenu.setCurrent(game);
			}
		}else if(HvlMenu.getCurrent() == game){
			String users = "";
			if(client.getTable().getPopulation(KC.key_Userlist()) > 0){
				for(String s : client.<ArrayList<String>>getValue(KC.key_Userlist())){
					users += "\n" + s;
				}
			}
			MainClient.font.drawWordc("Users:" + users, Display.getWidth()/2, Display.getHeight()/2, Color.white);
		}
		HvlMenu.updateMenus(delta);
	}

	public static void onDisconnection(){
		HvlMenu.setCurrent(main);
		main.getFirstArrangerBox().getFirstOfType(HvlLabeledButton.class).setEnabled(true);
	}

}
