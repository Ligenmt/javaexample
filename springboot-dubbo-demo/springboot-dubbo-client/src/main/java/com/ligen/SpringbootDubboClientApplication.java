package com.ligen;

import com.ligen.dubbo.model.User;
import com.ligen.dubbo.service.CustomerService;
import com.ligen.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath*:dubbo/dubbo-*",
        "classpath*:applicationContext-*"})
public class SpringbootDubboClientApplication implements CommandLineRunner {

    @Autowired
    CustomerService customerService;
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDubboClientApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        customerService.hello();
        User user = userService.findUser();
        System.out.println(user.getName());
    }
}
