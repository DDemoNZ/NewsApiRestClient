package com.newsapi.test.task;


import com.newsapi.test.task.client.ClientClass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TaskApplication {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);

        //Because it doesn't has any real client.
        ClientClass clientClass = new ClientClass();
        clientClass.doAllClientWork();
    }
}
