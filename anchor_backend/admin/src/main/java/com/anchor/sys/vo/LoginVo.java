package com.anchor.sys.vo;

import com.anchor.sys.domin.User;
import lombok.Data;

@Data
public class LoginVo extends User {
    private String verCode;
}
