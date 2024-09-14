package com.anchor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    // 测试接口
    @RequestMapping("/hello")
    public String hello() {
        return "Hello, apps!";
    }
}
