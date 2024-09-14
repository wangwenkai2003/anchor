package com.anchor.security.filter;

import com.anchor.auth.server.utils.JwtTokenServerUtils;
import com.anchor.auth.utils.JwtUserInfo;
import com.anchor.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenServerUtils jwtTokenServerUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        //解析token
        if (!StringUtils.hasText(token)){
            //放行
            filterChain.doFilter(request,response);
            return;
        }
        JwtUserInfo userInfo;
        try {
            userInfo = jwtTokenServerUtils.getUserInfo(token);
        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException();
        }
        //从redis中获取用户信息
        Object anchor = RedisUtils.getInstance().get("anchor", userInfo.getUserName());
        if (Objects.isNull(anchor)){
            throw  new RuntimeException("用户未登录");
        }
        List<SimpleGrantedAuthority> authorities = null;
        if ( userInfo.getPermissions()==null || userInfo.getPermissions().size()==0 ){
                authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }else {
         authorities =   userInfo.getPermissions().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        //存入SecurityContextHoler
        //todo 获取权限信息封装到authentication中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userInfo,null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
