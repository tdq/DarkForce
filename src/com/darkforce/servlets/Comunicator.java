package com.darkforce.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.darkforce.components.Application;
import com.darkforce.events.EventBus;
import com.google.gson.Gson;

@ServerEndpoint(value="/server")
public class Comunicator {
	private static Session session;
	
	@OnOpen
	public void onOpen(Session session) {
		Comunicator.session = session;
	}
	
	@OnMessage
	public void onMessage(Session session, String message) throws Exception {
		if(session.isOpen()) {
			Gson gson = new Gson();
			@SuppressWarnings("unchecked")
			HashMap<String, String> params = gson.fromJson(message, HashMap.class);
			
			switch(params.get("action")) {
			case "init":
				Application app = new Application();
				session.getBasicRemote().sendText(app.toString());
				break;
			case "close":
				session.close();
				break;
			
			case "event":
				EventBus.fire(params.get("id"), params.get("event"));
				break;
			}
		}
	}
	
	@OnClose
	public void onClose() {
		
	}
	
	public static void sendMessage(String message) throws IOException {
		if(session.isOpen()) {
			session.getBasicRemote().sendText(message);
		}
	}
}
