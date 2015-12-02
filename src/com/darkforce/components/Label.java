package com.darkforce.components;

import java.io.IOException;

import com.darkforce.servlets.Comunicator;

public class Label implements Component {
	public static final String HIDE = "hide";
	public static final String SHOW = "show";
	private String id;
	private String value;
	
	public Label(String id) {
		this.id = id;
	}
	
	public void setValue(String value) {
		this.value = value;
		try {
			this.update();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void update() throws IOException {
		Comunicator.sendMessage("{\"action\":\"update\",\"components\":"+toString()+"}");
	}

	public String getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return "{\"type\":\"label\",\"id\":\""+id+"\",\"value\":\""+value+"\"}";
	}
}
