package com.pyrogusto.websocket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class EchoClient {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
        try {
            String name = "Duke";
            System.out.println("Sending message to endpoint: " + name);
            session.getBasicRemote().sendText(name);
        } catch (IOException ex) {
            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @OnMessage
    public void processMessage(String message) {
        System.out.println("Received message in client: " + message);
    }
    
    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }
    
    public static void main(String[] args)throws Exception{
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8080/WebSocketServerJ2EE7/websocket";
        System.out.println("Connecting to " + uri);
        container.connectToServer(EchoClient.class, URI.create(uri));
 	System.out.println("Enter anything to finish program");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferRead.readLine();
    }
}
