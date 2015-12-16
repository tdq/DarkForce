package com.myapp;

import com.darkforce.components.Application;
import com.darkforce.components.Button;
import com.darkforce.components.Label;

public class MyApp extends Application {

	@Override
	public void init() {
		Label label = new Label("label1");
		//label.setSessionId(sessionId);		// TODO find better solution
		label.setValue("Hello");
		
		Button button = new Button("button1");
		//button.setSessionId(sessionId);		// TODO find better solution
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
