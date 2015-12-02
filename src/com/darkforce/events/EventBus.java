package com.darkforce.events;

import java.util.HashMap;

import com.darkforce.components.ClickEvent;

public class EventBus {
	private static HashMap<String, HashMap<String, ClickEvent>> events = new HashMap<>();
	
	public static void fire(String id, String eventName) {
		if(events.containsKey(id) && events.get(id).containsKey(eventName)) {
			ClickEvent event = events.get(id).get(eventName);
			event.onClick();
		}
	}

	public static void addEvent(String id, String eventName, ClickEvent clickEvent) {
		HashMap<String, ClickEvent> componentEvents;
		
		if(events.containsKey(id) == false) {
			componentEvents = new HashMap<>();
			events.put(id, componentEvents);
		} else {
			componentEvents = events.get(id);
		}
		
		componentEvents.put(eventName, clickEvent);
	}
}
