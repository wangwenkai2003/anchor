package com.anchor.sys.service;

import com.anchor.auth.utils.Token;
import com.anchor.sys.domin.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;

public interface UserService extends IService<User> {
    public ResponseEntity<Token> login(User user);
    public ResponseEntity<String> logout();
    public String getUserOpenId(String code);
    public User getUserInfo(String userName);
    public Integer register(String username, String password,String nickName);
    public Integer existUser(String userName);
}
