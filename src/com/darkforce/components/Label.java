package com.darkforce.components;

import com.darkforce.events.DarkForce;

public class Label extends Component {
	public static final String HIDE = "hide";
	public static final String SHOW = "show";
	private String value;
	
	public Label(String id) {
		this.id = id;
	}
	
	public void setValue(String value) {
		this.value = value;
		this.update();
	}
	
	private void update() {
		DarkForce.update(this);
	}

	public String getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return "{\"type\":\"label\",\"id\":\""+id+"\",\"value\":\""+value+"\"}";
	}
}
