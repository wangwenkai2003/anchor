package com.anchor;

import com.anchor.auth.server.EnableAuthServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.anchor.*")
@SpringBootApplication
//新建模块需要添加包扫描
@EnableAuthServer
@EnableCaching
public class AnchorApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnchorApplication.class, args);
    }
}
