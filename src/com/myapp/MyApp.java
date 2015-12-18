package com.myapp;

import com.darkforce.components.Application;
import com.darkforce.components.Button;
import com.darkforce.components.Label;
import com.darkforce.components.VerticalLayout;

public class MyApp extends Application {

	@Override
	public void init() {
		VerticalLayout layout = new VerticalLayout("vertical1");
		Label label = new Label("label1");
		label.setValue("Hello");
		
		Button button = new Button("button1");
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
		
		Button newLabelButton = new Button("button3");
		newLabelButton.setValue("Create new label");
		newLabelButton.onClick(() -> {
			Label newlabel = new Label("label2");
			newlabel.setValue("New label!!!");
			layout.add(newlabel);
		});
		
		layout.add(label);
		layout.add(button);
		layout.add(hideButton);
		layout.add(newLabelButton);
		
		this.add(layout);
	}
}
