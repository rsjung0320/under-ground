package com.nextinno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class UnderGroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnderGroundApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Wrold";
    }
}
