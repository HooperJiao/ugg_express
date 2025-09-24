package com.hooper.ugg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hooper.ugg.mapper")
public class UggExpressApplication {

    public static void main(String[] args) {
        SpringApplication.run(UggExpressApplication.class, args);
    }

}
