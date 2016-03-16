package com.darkforce.components;

import com.darkforce.events.DarkForce;
import com.google.gson.Gson;
import java.util.HashMap;

public class InputText extends Component{
	private String value;
        private String placeholder;
	
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
            HashMap<String, String> command = new HashMap<>();
            
            command.put("type", "inputText");
            command.put("id", id);
            command.put("value", value);
            //command.put("palceholder", placeholder);
            
            Gson gson = new Gson();
            
            return gson.toJson(command);
	}

    public void placeHolder(String plaveholder) {
        this.placeholder = plaveholder;
        DarkForce.update(this);
    }
}
