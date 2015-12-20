package com.darkforce.components;

public class HorizontalLayout extends VerticalLayout {

	public HorizontalLayout(String id) {
		super(id);
	}

	@Override
	public String toString() {
		return "{\"type\":\"horizontal\",\"id\":\""+id+"\",\"components\":"+buildComponents()+"}";
	}
}
