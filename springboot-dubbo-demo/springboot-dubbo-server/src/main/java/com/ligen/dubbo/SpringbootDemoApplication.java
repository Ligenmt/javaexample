package com.ligen.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@ImportResource(locations = {"classpath*:dubbo/dubbo-*",
        "classpath*:applicationContext-*"})
public class SpringbootDemoApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringbootDemoApplication.class, args);

        Thread.sleep(9999999L);
    }
}
