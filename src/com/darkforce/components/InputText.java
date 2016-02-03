package com.darkforce.components;

import com.darkforce.events.DarkForce;

public class InputText extends Component{
	private String value;
	
	public InputText(String id) {
		this.id = id;
		DarkForce.addEvent(this, "change", (value) -> {
			this.value = value;
		});
	}
	
	public void setValue(String value) {
		this.value = value;
		DarkForce.update(this);
	}
	
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return "{\"type\":\"inputText\",\"id\":\""+id+"\",\"value\":\""+this.value+"\"}";
	}
}
