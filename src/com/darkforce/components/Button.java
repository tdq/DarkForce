package com.darkforce.components;

import com.darkforce.events.DarkForce;

public class Button extends Component {
    private String value;
    private String bindedAction = "";

    public interface ClickEvent {
        public void onClick();
    }

    public Button(String id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{\"type\":\"button\",\"id\":\""+id+"\",\"value\":\""+value+"\""+bindedAction+"}";
    }

    public void onClick(ClickEvent clickEvent) {
        DarkForce.addEvent(this, "click", (String value) -> clickEvent.onClick());
    }

    public void bindClick(String id, String action) {
        bindedAction = ",\"bind\":{\"id\":\""+id+"\",\"action\":\""+action+"\"}";
    }

    /*
    public void click() {
        if(clickEvent != null)
            clickEvent.onClick();
    }
    */
}
