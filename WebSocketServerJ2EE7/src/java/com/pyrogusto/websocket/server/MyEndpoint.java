
package com.pyrogusto.websocket.server;


import com.pyrogusto.websocket.Figure;
import com.pyrogusto.websocket.FigureDecoder;
import com.pyrogusto.websocket.FigureEncoder;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonBuilderFactory;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


// ws://localhost:8080/WebSocketServerJ2EE7/websocket
@ServerEndpoint(value="/websocket",encoders = {FigureEncoder.class}, decoders = {FigureDecoder.class})
public class MyEndpoint {
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen (Session peer) {
        System.out.println("onOpen");
        peers.add(peer);
    }

    @OnClose
    public void onClose (Session peer) {
        System.out.println("onClose");
        
        peers.remove(peer);
    }    
    /*
    @OnMessage
    public Figure echoText(String name) {
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
    */
    @OnMessage
    public void broadcastFigure(Figure figure, Session session) throws IOException, EncodeException {
        System.out.println("broadcastFigure: " + figure);
        for (Session peer : peers) {
            //if (!peer.equals(session)) {
                peer.getBasicRemote().sendObject(figure);
            //}
        }
    }    
    
}