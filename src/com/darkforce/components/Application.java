package com.darkforce.components;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Application {
	private List<Component> components = new LinkedList<>();
	private String sessionId;
	
	public Application(String sessionId) {
		this.sessionId = sessionId;
	}

	private void add(Component component) {
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

	public void init() {
		Label label = new Label("label1");
		label.setSessionId(sessionId);		// TODO find better solution
		label.setValue("Hello");
		
		Button button = new Button("button1");
		button.setSessionId(sessionId);		// TODO find better solution
		button.setValue("Click me");
		button.onClick(() -> {
			Thread th = new Thread(new Runnable() {
				
				@Override
				public void run() {
					for(int i = 0; i<=1000; ++i) {
						label.setValue(label.getSessionId()+' '+String.valueOf(i));
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			
			th.start();
		});
		
		button.bindClick("label1", Label.SHOW);
		
		Button hideButton = new Button("button2");
		hideButton.setValue("Hide label");
		hideButton.bindClick("label1", Label.HIDE);
		
		this.add(label);
		this.add(button);
		this.add(hideButton);
	}
}
