package com.darkforce.components;

import com.darkforce.servlets.Communicator;

public class Component {

	private final String sessionId = Communicator.sessionId.get();
	private String domId;
	private static long componentCounter = 0l;
	private String componentId;

	public Component() {
		componentCounter++;
		componentId = "Component"+componentCounter;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public void setId(String id) {
		this.domId = id;
	}

	public String getId() {
		return domId != null && domId.isEmpty() == false ? domId : componentId;
	}

}
