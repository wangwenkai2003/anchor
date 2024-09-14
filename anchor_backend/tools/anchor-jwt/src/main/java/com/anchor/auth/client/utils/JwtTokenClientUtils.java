package com.anchor.auth.client.utils;

import com.anchor.auth.client.properties.AuthClientProperties;
import com.anchor.auth.utils.JwtHelper;
import com.anchor.auth.utils.JwtUserInfo;
import lombok.AllArgsConstructor;

/**
 * jwtToken 客服端工具
 */
@AllArgsConstructor
public class JwtTokenClientUtils {

    private AuthClientProperties authClientProperties;

    public JwtUserInfo getUserInfo(String token) {
        AuthClientProperties.TokenInfo userInfo =authClientProperties.getUser();
        return JwtHelper.getJwtFromToken(token, userInfo.getPubKey());
    }

}
