package com.anchor.sys.controller.login;

import com.anchor.auth.server.utils.JwtTokenServerUtils;
import com.anchor.auth.utils.Token;
import com.anchor.security.util.VerificationCodeUtil;
import com.anchor.sys.domin.User;
import com.anchor.redis.RedisUtils;
import com.anchor.sys.domin.WexinUser;
import com.anchor.sys.service.UserService;
import com.anchor.sys.vo.LoginVo;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.concurrent.TimeUnit;

@RequestMapping("/sys")
@RestController
@Slf4j
public class LoginController {

    @Autowired
    JwtTokenServerUtils jwtTokenServerUtils;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    UserService userService;
    //登录
    @PostMapping("/login")
    public ResponseEntity user(@RequestBody LoginVo loginVo, HttpServletRequest request){
        // 获取并移除session中的验证码，防止重复使用
        String verCode = loginVo.getVerCode();
        User user = loginVo;
        String sessionCode =(String) redisUtils.get("captcha:" + request.getRemoteAddr());
        redisUtils.delete("captcha:" + request.getRemoteAddr());
        if (verCode == null || !sessionCode.equalsIgnoreCase(verCode.trim())) {
            log.warn("验证码验证失败，用户输入({})与服务器存储({})不匹配", verCode, sessionCode);
            return ResponseEntity.status(406).body("406");
        }

        // 登录逻辑
        ResponseEntity<Token> tokenResponse = userService.login(user);
        // 若登录成功，可考虑在此处清除所有与本次登录尝试相关的session属性（如验证码）
        // request.getSession().invalidate(); // 这句视具体需求决定是否启用

        return ResponseEntity.status(200).body(tokenResponse);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody LoginVo loginVo, HttpServletRequest request){
        // 获取并移除session中的验证码，防止重复使用
        String verCode = loginVo.getVerCode();
        User user = loginVo;
        String sessionCode =(String) redisUtils.get("captcha:" + request.getRemoteAddr());
        redisUtils.delete("captcha:" + request.getRemoteAddr());
        if (verCode == null || !sessionCode.equalsIgnoreCase(verCode.trim())) {
            log.warn("验证码验证失败，用户输入({})与服务器存储({})不匹配", verCode, sessionCode);
            return ResponseEntity.status(406).body("406");
        }
        String passwordEnd = new BCryptPasswordEncoder().encode(user.getPassword());

        Integer register = userService.register(user.getUserName(), passwordEnd, user.getNickName());

        // request.getSession().invalidate(); // 这句视具体需求决定是否启用
        return register>=1?ResponseEntity.status(200).body("200"):ResponseEntity.status(500).body("服务器异常，注册失败");
    }

    //获取验证码
    @GetMapping("/getCode")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 创建并配置验证码
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

//        // 设置验证码有效时间为5分钟（举例），可以根据实际情况调整
//        request.getSession().setAttribute("captcha", specCaptcha.text().toLowerCase());
//        request.getSession().setMaxInactiveInterval(5 * 60); // 设置session过期时间
        redisUtils.set("captcha:"+ request.getRemoteAddr(),specCaptcha.text().toLowerCase(), 60);
        // 设置响应头
        response.setContentType("image/gif");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 更严格的缓存控制
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // 输出验证码图片
        specCaptcha.out(response.getOutputStream());
    }



    // 获取用户信息接口
    @GetMapping("/getUser")
//    @PreAuthorize("hasAuthority('sys:user:get')")
    public User user(String userName) {
        // 获取当前登录的用户信息
        User userInfo = userService.getUserInfo(userName);
        return userInfo;
    }

    @GetMapping("/existUser")
    public Integer existUser(String userName) {
        Integer integer = userService.existUser(userName);
        return integer;
    }

    // 注销接口
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseEntity<String> logout = userService.logout();
        return logout;
    }

    // 微信登录接口
    @PostMapping("wexin/login")
    public ResponseEntity<String> wexinLogin(String code){
        // 获取用户openId
        String token = userService.getUserOpenId(code);

        return ResponseEntity.ok(token);
    }
}
