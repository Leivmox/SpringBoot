package com.example.springboot.conf.beansTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class MyConfig {
    @Bean
    public MyService myService2() {
        return new MyService();
    }
}

