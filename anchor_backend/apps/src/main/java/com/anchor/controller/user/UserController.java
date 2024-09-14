package com.anchor.controller.user;


import com.anchor.auth.server.utils.JwtTokenServerUtils;
import com.anchor.auth.utils.JwtUserInfo;
import com.anchor.auth.utils.Token;
import com.anchor.sys.domin.User;
import com.anchor.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    JwtTokenServerUtils jwtTokenServerUtils;

    @Autowired
    RedisUtils redisUtils;

    @PostMapping("/login")
    public Token user(@RequestBody User user){
        JwtUserInfo jwtUserInfo = new JwtUserInfo();
        jwtUserInfo.setId(user.getId());
//        jwtUserInfo.setAccount(user.());
//        jwtUserInfo.setName(user.getName());
        Token token = jwtTokenServerUtils.generateUserToken(jwtUserInfo, 10000);
        redisUtils.set("anchor","user",token);
        return token;
    }
    @PreAuthorize("hasAuthority('test')")
    @GetMapping("/getUser")
    public User user(String token) {
        // 获取当前登录的用户信息
        User user = new User();
        JwtUserInfo userInfo = jwtTokenServerUtils.getUserInfo(token);
        String account = userInfo.getUserName();


        Pattern pattern = Pattern.compile("token=(.*?),");

        user.setId(userInfo.getId());
        return user;
    }

    @PreAuthorize("hasAuthority('xxxx')")
    @GetMapping("/getMethod")
    public String getMethod() {

        return "xxxx";
    }

}
