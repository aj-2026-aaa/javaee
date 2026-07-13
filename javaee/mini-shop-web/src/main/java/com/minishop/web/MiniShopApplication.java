package com.minishop.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.minishop")
@MapperScan("com.minishop.**.mapper")
public class MiniShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniShopApplication.class, args);
    }
}
