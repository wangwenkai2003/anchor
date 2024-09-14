package com.anchor.auth.client.configuration;

import com.anchor.auth.client.properties.AuthClientProperties;
import com.anchor.auth.client.utils.JwtTokenClientUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * auth配置
 */
@EnableConfigurationProperties(value =  {AuthClientProperties.class})
public class AuthClientConfiguration {
    @Bean
    public JwtTokenClientUtils getJwtTokenClientUtils(AuthClientProperties authClientProperties) {
        return new JwtTokenClientUtils(authClientProperties);
    }
}
