package com.darkforce.components;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import com.darkforce.events.DarkForce;

public class VerticalLayout extends Component {
	private LinkedList<Component> components = new LinkedList<>();
	private HashSet<String> componentsIds = new HashSet<>();
	
	public VerticalLayout(String id) {
		this.id = id;
	}

	public void add(Component component) {
		if(componentsIds.contains(component.id) == false) {
			components.add(component);
			componentsIds.add(component.id);
			DarkForce.update(this);
		}
	}
	
	protected String buildComponents() {
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
		return "{\"type\":\"vertical\",\"id\":\""+id+"\",\"components\":"+buildComponents()+"}";
	}
}
