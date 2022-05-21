package com.example.blogapiconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class BlogApiConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApiConfigServerApplication.class, args);
    }

}
