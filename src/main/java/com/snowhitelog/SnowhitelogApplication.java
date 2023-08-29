package com.snowhitelog;

import com.snowhitelog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class SnowhitelogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowhitelogApplication.class, args);
    }

}
