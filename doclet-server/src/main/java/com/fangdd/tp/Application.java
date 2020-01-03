package com.fangdd.tp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author xuwenzhen
 */
@EnableAsync
@EnableWebMvc
@SpringBootApplication(scanBasePackages = "com.fangdd", exclude = {MongoAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        new SpringApplication(Application.class).run(args);
    }
}
