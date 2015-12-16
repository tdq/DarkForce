package com.darkforce.components;

import com.darkforce.servlets.Communicator;

public class Component {

	private String sessionId = Communicator.sessionId.get();
	protected String id;

	public String getSessionId() {
		return sessionId;
	}

	public String getId() {
		return id;
	}

}
