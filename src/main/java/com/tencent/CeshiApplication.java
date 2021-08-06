package com.tencent;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages="com.tencent.dao")
@EnableScheduling
public class CeshiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CeshiApplication.class, args);
    }

}
