package com.anchor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gen")
public class GeneratorController {
    // 测试接口
    @RequestMapping("/hello")
    public String hello() {
        return "Hello, Gen!";
    }


}
