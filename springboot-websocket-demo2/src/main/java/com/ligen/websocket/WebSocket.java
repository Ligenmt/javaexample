package com.ligen.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ServerEndpoint("/socket/{uid}")  // 接口路径 ws://localhost:8087/websocket/userId;
public class WebSocket {

    /**
     * 记录当前在线连接数
     */
    private static int onlineCount = 0;

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 接收客户端消息的uid
     */
    private String uid;

    // 用来存在线连接用户信息
    private final static ConcurrentHashMap<String,Session> sessionPool = new ConcurrentHashMap<>();

    /**
     * 使用线程安全的ConcurrentHashMap来存放每个客户端对应的WebSocket对象
     */
    private static ConcurrentHashMap<String, WebSocket> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) {
        try {
            this.session = session;
            this.uid = uid;
            if (webSocketMap.containsKey(uid)) {
                webSocketMap.remove(uid);
                //加入到set中
                webSocketMap.put(uid, this);
            } else {
                //加入set中
                webSocketMap.put(uid, this);
                //在线数加1
                addOnlineCount();
            }
            sessionPool.put(session.getId(), session);
            log.info("用户【" + uid + "】连接成功，当前在线人数为:" + getOnlineCount());
            try {
                sendMsg("连接成功");
            } catch (IOException e) {
                log.error("用户【" + uid + "】网络异常!", e);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(uid)) {
            webSocketMap.remove(uid);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户【" + uid + "】退出，当前在线人数为:" + getOnlineCount());

    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("用户【" + uid + "】发送报文:" + message);
    }

    /** 发送错误时的处理
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户【" + this.uid + "】处理消息错误，原因:" + error.getMessage());
        error.printStackTrace();
    }


    // 此为广播消息
    public void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:" + message);
        for(WebSocket webSocket : webSocketMap.values()) {
            try {
                if(webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null&&session.isOpen()) {
            try {
                log.info("【websocket消息】 单点消息:"+message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 实现服务器主动推送
     * @param msg
     * @throws IOException
     */
    private void sendMsg(String msg) throws IOException {
        this.session.getBasicRemote().sendText(msg);
    }

    // 此为单点消息(多人)
    public void sendMoreMessage(String[] userIds, String message) {
        for(String userId:userIds) {
            Session session = sessionPool.get(userId);
            if (session != null&&session.isOpen()) {
                try {
                    log.info("【websocket消息】 单点消息:"+message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }
    private static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }
    private static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

}