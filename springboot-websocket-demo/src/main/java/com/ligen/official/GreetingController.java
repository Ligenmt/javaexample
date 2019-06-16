package com.ligen.official;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.HtmlUtils;

import javax.websocket.Session;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by ligen on 2018/5/12.
 */
@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(100); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!", "2");
    }

    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;
    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @Scheduled(fixedRate = 30000)
    public void sendMessage(){
        brokerMessagingTemplate.convertAndSend("/topic/greetings", new Greeting("Hello, I am Server", "1"));
        Set<SimpUser> users = simpUserRegistry.getUsers();
        System.out.println(users.size());
    }

    @RequestMapping("/send")
    @ResponseBody
    public String send() {
        Set<SimpUser> users = simpUserRegistry.getUsers();
        Iterator<SimpUser> i = users.iterator();
        while (i.hasNext()) {
            String name = i.next().getName();
            System.out.println(name);
            brokerMessagingTemplate.convertAndSendToUser(name, "/queue/msg", new Greeting(name + ", I am Server", "3"));
        }
        return "ok";
    }

    @RequestMapping("/sendAll")
    @ResponseBody
    public String sendAll() {
        brokerMessagingTemplate.convertAndSend("/topic/hello", new Greeting("Hi, I am Server", "4"));
        return "ok";
    }
}