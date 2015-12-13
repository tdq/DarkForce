# DarkForce

Requirements:

1. Java 8
2. Tomcat 8
3. Maven

TODOS:

1. Split connections by user sessions (Done, but I need better solution)
2. Wrap Session.getBasicRemote().sendText() to DarkForce.send() method (Done)
3. Store separate EventBus for each user session (Done)
4. Update components states on server
5. Process forms
6. Layouts