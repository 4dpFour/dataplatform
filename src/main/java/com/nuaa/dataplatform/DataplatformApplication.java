package com.nuaa.dataplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
@SpringBootApplication
public class DataplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataplatformApplication.class, args);
    }

}
