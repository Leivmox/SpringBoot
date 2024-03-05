package com.example.springboot.domain.testProrperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:test.properties")
@EnableConfigurationProperties(MyProperties.class)
@ConfigurationProperties(prefix = "test")

public class MyProperties {

        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "test{" +
                    "name='" + name + '\'' +
                    ", id=" + id +
                    '}';
        }
    }


