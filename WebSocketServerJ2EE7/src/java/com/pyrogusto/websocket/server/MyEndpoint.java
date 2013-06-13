
package com.pyrogusto.websocket.server;


import java.io.IOException;
import java.nio.ByteBuffer;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


// ws://localhost:8080/WebSocketServerJ2EE7/websocket
@ServerEndpoint(value="/websocket")
public class MyEndpoint {
    
    @OnMessage
    public String echoText(String name) {
        System.out.println("echoText"+name);
        return name;
    }

    @OnMessage
    public void echoBinary(byte[] data, Session session) throws IOException {
        System.out.println("echoBinary: " + data);
        StringBuilder builder = new StringBuilder();
        for (byte b : data) {
            builder.append(b);
        }
        System.out.println(builder);
        session.getBasicRemote().sendBinary(ByteBuffer.wrap(data));
    }

//    @WebSocketMessage
//    public void echoBinary(ByteBuffer data, Session session) throws IOException {
//        System.out.println("echoBinary: " + data);
//        StringBuilder builder = new StringBuilder();
//        for (byte b : data.array()) {
//            builder.append(b);
//        }
//        System.out.println(builder);
//        session.getRemote().sendBytes(data);
//    }
}