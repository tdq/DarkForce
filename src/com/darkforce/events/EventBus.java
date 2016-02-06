package com.darkforce.events;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.darkforce.components.Event;

public class EventBus {
	private LinkedHashMap<String, HashMap<String, Event>> events = new LinkedHashMap<>();
	
	public void fire(String id, String eventName, String value) {
		if(events.containsKey(id) && events.get(id).containsKey(eventName)) {
			Event event = events.get(id).get(eventName);
			event.onEvent(value);	//TODO I have to think how to proceed parameters for some events
		}
	}

	public void addEvent(String id, String eventName, Event event) {
		HashMap<String, Event> componentEvents;
		
		if(events.containsKey(id) == false) {
			componentEvents = new HashMap<>();
			events.put(id, componentEvents);
		} else {
			componentEvents = events.get(id);
		}
		
		componentEvents.put(eventName, event);
	}
}
