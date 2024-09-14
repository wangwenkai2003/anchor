package com.anchor.sys.domin;

import com.anchor.domain.UserDto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;


/**
 * 用户表(User)实体类
 *
 * @author 三更
 */
@Data
@TableName("sys_user")
public class User extends UserDto implements Serializable {

}