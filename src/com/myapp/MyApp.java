package com.myapp;

import com.darkforce.components.Application;
import com.darkforce.components.Button;
import com.darkforce.components.HorizontalLayout;
import com.darkforce.components.InputText;
import com.darkforce.components.Label;
import com.darkforce.components.VerticalLayout;

public class MyApp extends Application {

    @Override
    public void init() {
        VerticalLayout vertical = new VerticalLayout();
        HorizontalLayout horizontal = new HorizontalLayout();

        Label label = new Label();
        label.setId("label1");
        label.setValue("Hello");

        Button button = new Button();
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

        Button hideButton = new Button();
        hideButton.setValue("Hide label");
        hideButton.bindClick("label1", Label.HIDE);

        Button newLabelButton = new Button();
        newLabelButton.setValue("Create new label");
        newLabelButton.onClick(() -> {
            Label newlabel = new Label();
            newlabel.setValue("New label!!!");
            horizontal.add(newlabel);
        });

        Label labels = new Label();
        labels.setValue("New labels:");
        horizontal.add(labels);

        InputText inputText = new InputText();
        //inputText.setValue("Type here");
        inputText.placeHolder("Type here");

        Button getTextButton = new Button();
        getTextButton.setValue("Get value");
        getTextButton.onClick(() -> {
            labels.setValue(inputText.getValue());
        });
        
        vertical.add(label);
        vertical.add(button);
        vertical.add(hideButton);
        vertical.add(newLabelButton);
        vertical.add(horizontal);
        vertical.add(inputText);
        vertical.add(getTextButton);

        this.add(vertical);
    }
}
