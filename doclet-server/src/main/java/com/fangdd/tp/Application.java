package com.fangdd.tp;

import com.fangdd.tp.doclet.enums.EnvEnum;
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
        SpringApplication app = new SpringApplication(Application.class);

//        if (SeedConstant.getEnv() == EnvEnum.DEVELOPMENT) {
            //开发环境时，使用开发环境的logback配置
            System.setProperty("logging.config", "classpath:logback-dev.xml");
//        }
        app.run(args);
    }
}
