package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
// See https://www.baeldung.com/java-websockets to review the use of parameters in the URI of the
// endpoint both in @ServerEndpoint annotation and in methods
// See that link as well for a review and example of use of the Java API for WebSocket
@ServerEndpoint(value = "/chat/{username}",
                encoders = MessageEncoder.class)
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(Message msg) throws IOException, EncodeException {
        //TODO: add send message method.
        //See how to iterate through a ConcurrentHashMap:
        //https://javarevisited.blogspot.com/2016/08/how-to-iterate-through-ConcurrentHashMap-print-all-keys-values-java.html
        String jsonStr = JSON.toJSONString(msg);

        for (String key : onlineSessions.keySet()) {
            System.out.println("WebSocketChatServer - Message " + jsonStr + " sent to client with username " + key + " and session " + onlineSessions.get(key));
            System.out.println(key + " : " + onlineSessions.get(key));
            try {
                onlineSessions.get(key).getBasicRemote().sendObject(msg);
            } catch (IOException | EncodeException error) {
                error.printStackTrace();
            }
        }
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {
        //TODO: add on open connection.
        onlineSessions.put(username, session);
        System.out.println("WebSocketChatServer - Connection opened for user " + username + " and session " + session);

        Message newMessage = new Message("Chat Server", username + " has joined the chat", Message.Type.ENTER, onlineSessions.size());
        String jsonStr = JSON.toJSONString(newMessage);
        System.out.println("WebSocketChatServer - Message " + jsonStr + " received from WebSocketChatServer with username " + newMessage.getUsername() + ", message " + newMessage.getMsg() + ", type " + newMessage.getType() + ", onlineCount " + newMessage.getOnlineCount() + " and session " + session);
        sendMessageToAll(newMessage);
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) throws IOException, EncodeException {
        //TODO: add send message.
        // See how to parse JSON strings into Java objects: https://www.baeldung.com/fastjson
        Message newMessage = JSON.parseObject(jsonStr, Message.class);

        System.out.println("WebSocketChatServer - Message " + jsonStr + " received from client with username " + newMessage.getUsername() + ", message " + newMessage.getMsg() + ", type " + newMessage.getType() + ", onlineCount " + newMessage.getOnlineCount() + " and session " + session);

        sendMessageToAll(newMessage);
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) throws IOException, EncodeException {
        //TODO: add close connection.
        onlineSessions.remove(username, session);
        System.out.println("WebSocketChatServer - Connection closed for user " + username + " and session " + session);

        Message newMessage = new Message("Chat Server", username + " has left the chat", Message.Type.LEAVE, onlineSessions.size());
        String jsonStr = JSON.toJSONString(newMessage);
        System.out.println("WebSocketChatServer - Message " + jsonStr + " received from WebSocketChatServer with username " + newMessage.getUsername() + ", message " + newMessage.getMsg() + ", type " + newMessage.getType() + ", onlineCount " + newMessage.getOnlineCount() + " and session " + session);
        sendMessageToAll(newMessage);
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}