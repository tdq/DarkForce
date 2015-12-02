package com.darkforce.components;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Application {
	private List<Component> components = new LinkedList<>();
	
	public Application() throws IOException {
		Label label = new Label("label1");
		label.setValue("Hello");
		
		Button button = new Button("button1");
		button.setValue("Click me");
		button.onClick(() -> {
			Thread th = new Thread(new Runnable() {
				
				@Override
				public void run() {
					for(int i = 0; i<=10; ++i) {
						label.setValue(String.valueOf(i));
						try {
							Thread.sleep(1000);
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

	private void add(Component component) {
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
}
