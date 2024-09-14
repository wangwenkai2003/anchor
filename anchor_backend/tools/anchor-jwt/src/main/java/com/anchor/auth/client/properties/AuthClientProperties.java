package com.anchor.auth.client.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.anchor.auth.client.properties.AuthClientProperties.PREFIX;

//获取auth配置
@ConfigurationProperties(prefix = PREFIX)
@Data
@AllArgsConstructor
public class AuthClientProperties {
    public static final String PREFIX = "authentication";
    private TokenInfo user;
    @Data
    public static class TokenInfo {
        /**
         * 请求头名称
         */
        private String headerName;
        /**
         * 解密 网关使用
         */
        private String pubKey;
    }
}
