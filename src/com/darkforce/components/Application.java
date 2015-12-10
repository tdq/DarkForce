package com.darkforce.components;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.darkforce.meta.Shared;

public abstract class Application {
	private List<Component> components = new LinkedList<>();
	
	@Shared(name = "sessionId")
	protected String sessionId;

	protected void add(Component component) {
		component.setSessionId(sessionId);
		this.components.add(component);
	}

	private String buildComponents() {
		StringBuilder builder = new StringBuilder();
		Iterator<Component> iter = components.iterator();
		
		builder.append('[');
		
		while(iter.hasNext()) {
			builder.append(iter.next());
			if(iter.hasNext())
				builder.append(',');
		}
		
		builder.append(']');
		
		return builder.toString();
	}
	
	@Override
	public String toString() {
		return "{\"action\":\"create\",\"components\":"+buildComponents()+"}";
	}

	public abstract void init();

	public void setSessionId(String id) {
		this.sessionId = id;
	}
}
