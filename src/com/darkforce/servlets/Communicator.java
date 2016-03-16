package com.darkforce.servlets;

import java.util.HashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.darkforce.components.Application;
import com.darkforce.events.DarkForce;
import com.google.gson.Gson;
import com.myapp.MyApp;

@ServerEndpoint(value="/server")
public class Communicator {
    public static ThreadLocal<String> sessionId = new ThreadLocal<>();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Start session");
        DarkForce.registerSession(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws Exception {
        if(session.isOpen()) {
            Gson gson = new Gson();
            @SuppressWarnings("unchecked")
            HashMap<String, String> params = gson.fromJson(message, HashMap.class);

            switch(params.get("action")) {
            case "event":
                DarkForce.fireEvent(session.getId(), params.get("id"), params.get("event"), params.get("value"));
                break;

            case "init":
                Application app = new MyApp();		// TODO use reflection to get necessary application class from application.xml description
                sessionId.set(session.getId());
                app.init();
                session.getBasicRemote().sendText(app.toString());
                break;
            case "close":
                session.close();
                break;
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Close session");
        DarkForce.removeSession(session);
    }
}
