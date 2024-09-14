package com.anchor.sys.service;

import com.anchor.auth.utils.JwtUserInfo;
import com.anchor.cores.ErrorConstants;
import com.anchor.sys.domin.User;
import com.anchor.sys.domin.LoginUser;
import com.anchor.sys.mapper.MenuMapper;
import com.anchor.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // 通过用户名从数据库获取用户信息
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(User::getUserName,name);

        User userInfo = userMapper.select(name);

        // 用户不存在
        if ( userInfo == null ) {
            throw new AccountExpiredException ( ErrorConstants.LOGIN_ERROR_NOTFOUND );
        }
//        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("test"));

        List<String> list = menuMapper.selectPermsByUserId(userInfo.getId());

        List<String> authorities = list;
        // 权限集合
//        List <String> authorities = new ArrayList (Arrays.asList("test") );
        return new LoginUser(userInfo,authorities);
    }
}

