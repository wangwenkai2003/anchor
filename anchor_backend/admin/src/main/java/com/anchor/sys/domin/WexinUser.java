package com.anchor.sys.domin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class WexinUser implements Serializable {
     // 用户头像链接
    private String avatarUrl;

    // 城市
    private String city;

    // 国家
    private String country;

    // 性别：0未知，1男，2女
    private int gender;

    // 是否降权或者禁言等特殊状态标志（假设is_demote代表此类含义）
    private boolean isDemoted;

    // 用户使用的语言
    private String language;

    // 昵称
    private String nickName;

    // 省份
    private String province;
}
