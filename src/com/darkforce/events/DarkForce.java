package com.darkforce.events;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.websocket.Session;

import com.darkforce.components.ClickEvent;
import com.darkforce.components.Component;

public class DarkForce {
	private static ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<>();
	private static ConcurrentMap<String, EventBus> events = new ConcurrentHashMap<>();

	public static void addEvent(Component component, String action, ClickEvent clickEvent) {
		events.get(component.getSessionId()).addEvent(component.getId(), action, clickEvent);
	}

	private static void send(String sessionId, String message) {
		try {
			if(sessionId != null && sessions.containsKey(sessionId)) {
				sessions.get(sessionId).getBasicRemote().sendText(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void update(Component component) {
		send(component.getSessionId(), "{\"action\":\"update\",\"components\":"+component.toString()+"}");
	}

	public static void registerSession(Session session) {
		events.put(session.getId(), new EventBus());
		sessions.put(session.getId(), session);
	}

	public static void removeSession(Session session) {
		sessions.remove(session.getId());
		events.remove(session.getId());
	}

	public static void fireEvent(String sessionId, String componentId, String action) {
		if(sessionId != null && sessions.containsKey(sessionId)) {
			events.get(sessionId).fire(componentId, action);
		}
	}
}