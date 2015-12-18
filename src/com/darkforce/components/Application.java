package com.darkforce.components;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Application {
	private List<Component> components = new LinkedList<>();

	protected void add(Component component) {
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
}
