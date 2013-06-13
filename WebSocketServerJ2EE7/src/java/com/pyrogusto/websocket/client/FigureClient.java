package com.pyrogusto.websocket.client;

import com.pyrogusto.websocket.Figure;
import com.pyrogusto.websocket.FigureDecoder;
import com.pyrogusto.websocket.FigureEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonBuilderFactory;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint(encoders = {FigureEncoder.class}, decoders = {FigureDecoder.class})
public class FigureClient {
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + session.getBasicRemote());
        try {
            Figure figure = createFiqure();
            session.getBasicRemote().sendObject(figure);
        } catch (Exception ex) {
            ex.printStackTrace();
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
        container.connectToServer(FigureClient.class, URI.create(uri));
 	System.out.println("Enter anything to finish program");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferRead.readLine();
    }    
    
    private static Figure createFiqure(){
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonArray jsonArray = factory.createArrayBuilder()
                .add(factory.createObjectBuilder().
                add("type", "home").
                add("number", "(800) 111-1111"))
                .add(factory.createObjectBuilder().
                add("type", "cell").
                add("number", "(800) 222-2222")).build();
        
        Figure figure = new Figure(jsonArray.getJsonObject(0));
        return figure;
    }
}
