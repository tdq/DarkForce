package com.darkforce.components;

import com.darkforce.meta.Autowired;

public class Component {
	@Autowired(value="sessionId")
	private String sessionId;
	protected String id;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getId() {
		return id;
	}

}
