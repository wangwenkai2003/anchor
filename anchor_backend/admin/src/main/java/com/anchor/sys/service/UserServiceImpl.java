package com.anchor.sys.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.anchor.auth.server.utils.JwtTokenServerUtils;
import com.anchor.auth.utils.JwtUserInfo;
import com.anchor.auth.utils.Token;
import com.anchor.domain.UserDto;
import com.anchor.redis.RedisUtils;
import com.anchor.sys.domin.LoginUser;
import com.anchor.sys.domin.User;
import com.anchor.sys.mapper.UserMapper;
import com.anchor.sys.util.SecurityUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtTokenServerUtils jwtTokenServerUtils;

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public ResponseEntity<Token> login(User user) {
        Authentication authenticate = SecurityUtils.login(user.getUserName(), user.getPassword(), authenticationManager);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        UserDto userDto = loginUser.getUser();
        JwtUserInfo jwtUserInfo = new JwtUserInfo();
        jwtUserInfo.setUserName(userDto.getUserName());
        jwtUserInfo.setId(userDto.getId());
        jwtUserInfo.setPermissions(loginUser.getPermissions());
//        jwtUserInfo.setUserType(userDto.getUserType());

        Token xtoken = jwtTokenServerUtils.generateUserToken( jwtUserInfo, 10000);
        redisUtils.set("anchor",String.valueOf(user.getUserName()),xtoken);
        return ResponseEntity.ok(xtoken);
    }

    @Override
    public ResponseEntity<String> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserInfo jwtUserInfo= (JwtUserInfo) authentication.getPrincipal();
        redisUtils.delete("anchor",jwtUserInfo.getUserName());
        return ResponseEntity.ok("退出成功");
    }

    @Override
    public String getUserOpenId(String code) {
         String authUrl="https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";
         authUrl=authUrl+"&appid="+"wx94ba18de21fa2d2e" + "&secret="+"3b4c83de6bbdddb565429e36d635f66f"+"&js_code="+code;
        String result = HttpUtil.get(authUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        String openid = (String) jsonObject.get("openid");
        JwtUserInfo jwtUserInfo = new JwtUserInfo();
        jwtUserInfo.setUserName(openid);
        Token token = jwtTokenServerUtils.generateUserToken(jwtUserInfo, 365);
        redisUtils.set("anchor",openid,token);
        return token.getToken();
    }

    @Override
    public User getUserInfo(String userName) {
        User userInfo = userMapper.getUserInfo(userName);
        return userInfo;
    }

    @Override
    public Integer register(String username, String password, String nickName) {
        Integer register = userMapper.register(username, password, nickName);
        return register;
    }

    @Override
    public Integer existUser(String userName) {
        Integer existUser = userMapper.existUser(userName);
        return existUser;
    }
}
