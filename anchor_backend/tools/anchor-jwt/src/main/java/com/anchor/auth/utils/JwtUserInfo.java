package com.anchor.auth.utils;

import com.alibaba.fastjson.annotation.JSONField;
import com.anchor.domain.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * jwt 存储的 内容
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtUserInfo extends UserDto implements Serializable{
    private List<String> permissions;
}
