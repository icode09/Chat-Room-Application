package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * WebSocket message model
 */
public class Message {
    // TODO: add message model.

    @JSONField(name = "username")
    private String username;
    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "type")
    private Type type;
    @JSONField(name = "onlineCount")
    private int onlineCount;

    public enum Type {
        ENTER,
        CHAT,
        LEAVE
    }

    public Message(String username, String msg, Type type, int onlineCount) {
        this.username = username;
        this.msg = msg;
        this.type = type;
        this.onlineCount = onlineCount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String text) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setAction(Type action) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String client) {
        this.username = username;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) { this.onlineCount = onlineCount; }
}
