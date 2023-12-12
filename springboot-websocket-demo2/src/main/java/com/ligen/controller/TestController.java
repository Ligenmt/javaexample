package com.ligen.controller;

import com.ligen.websocket.WebSocket;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private WebSocket webSocket;

    @RequestMapping("/api/test")
    public String test() {
        webSocket.sendAllMessage("123");
        return "OK";
    }
}
