package com.darkforce.servlets;

import java.util.HashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.darkforce.components.Application;
import com.darkforce.events.DarkForce;
import com.google.gson.Gson;

@ServerEndpoint(value="/server")
public class Communicator {
	//private static ConcurrentMap<String, EventBus> events = new ConcurrentHashMap<>();
	
	@OnOpen
	public void onOpen(Session session) {
		DarkForce.registerSession(session);
	}
	
	@OnMessage
	public void onMessage(Session session, String message) throws Exception {
		if(session.isOpen()) {
			Gson gson = new Gson();
			@SuppressWarnings("unchecked")
			HashMap<String, String> params = gson.fromJson(message, HashMap.class);
			
			switch(params.get("action")) {
			case "init":
				Application app = new Application(session.getId());
				app.init();
				session.getBasicRemote().sendText(app.toString());
				break;
			case "close":
				session.close();
				break;
			
			case "event":
				DarkForce.fireEvent(session.getId(), params.get("id"), params.get("event"));
				break;
			}
		}
	}
	
	@OnClose
	public void onClose(Session session) {
		DarkForce.removeSession(session);
	}
}