package com.anchor.security.config;


import com.anchor.security.filter.JwtTokenFilter;
import com.anchor.security.handler.AccessDeniedHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@Order(0)
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法级安全验证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * 描述：设置授权处理相关的具体类以及加密方式
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider ( );
//        // 设置不隐藏 未找到用户异常
//        provider.setHideUserNotFoundExceptions ( true );
//        // 用户认证service - 查询数据库的逻辑
//        provider.setUserDetailsService ( userDetailsService ( ) );
//        // 设置密码加密算法
//        provider.setPasswordEncoder ( passwordEncoder ( ) );
////        provider.setUserDetailsService ( userDetailsService );
//        auth.authenticationProvider ( provider );
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .headers ( ).frameOptions ( ).disable ( ).and ( )
//                .exceptionHandling ( ).accessDeniedHandler ( accessDeniedHandler ).and ( )
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf ( ).disable ( )
//                .exceptionHandling ( ).authenticationEntryPoint ( unauthorizedHandler ).and ( )
                // 基于token，所以不需要session
                .sessionManagement ( ).sessionCreationPolicy ( SessionCreationPolicy.STATELESS ).and ( )
                .authorizeRequests ( )
                .antMatchers("/sys/login").anonymous( )
                .antMatchers ( "/401"
                        , "/404",
                        "/app/sys/login",
                        "/sys/sysConfig/getConfig",
                        "/sys/getCode",
                        "/app/sys/getCode",
                        "/sys/casLogin",
                        "/sys/wexin/login",
                        "/sys/getCode",
                        "/sys/register",
                        "/sys/existUser"
                        ).permitAll ( ) // 允许请求无需认证
                .antMatchers ( HttpMethod.OPTIONS, "/**" ).permitAll ( )
                .anyRequest ( ).authenticated ( ) ;// 所有请求都需要验证;

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);

        //允许跨域
        http.cors();
    }

    /**
     * 描述: 静态资源放行，这里的放行，是不走 Spring Security 过滤器链
     **/
    @Override
    public void configure(WebSecurity web) {
        // 可以直接访问的静态数据
        web.ignoring ( )
                .antMatchers (
                        "/doc.html",
                        "/swagger-ui.html",
                        "/userfiles/**",
                        "/static/**",
                        "/swagger**/**",
                        "/webjars/**" );
    }

    /**
     * 描述: 密码加密算法 BCrypt 推荐使用
     **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder ( );
    }
//
    public static void main(String[] args) {
        // 输出加密后的密码
        System.out.println(new BCryptPasswordEncoder().encode("zxc1020."));
        // 输出密码是否匹配
        System.out.println(new BCryptPasswordEncoder().matches("zxc1020.","$2a$10$GgJ4a17zhJbGQKHzq9RmAOvx4ZCMbsrScApCjzHUVA0pVkKQBUgGq"));
    }

    /**
     * 描述: 注入AuthenticationManager管理器
     **/
    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager ( );
    }
}
