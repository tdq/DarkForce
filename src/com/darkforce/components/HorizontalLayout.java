package com.darkforce.components;

public class HorizontalLayout extends VerticalLayout {
	
    @Override
    public String toString() {
        return "{\"type\":\"horizontal\",\"id\":\""+getId()+"\",\"components\":"+buildComponents()+"}";
    }
}
