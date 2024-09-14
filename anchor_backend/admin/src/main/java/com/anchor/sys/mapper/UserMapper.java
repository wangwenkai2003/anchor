package com.anchor.sys.mapper;

import com.anchor.sys.domin.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select id,user_name,password from sys_user where user_name = #{name}")
    public User select(String name);

    @Insert("insert into sys_user(user_name,password,nick_name) values(#{username},#{password},#{nickName})")
    public Integer register(String username, String password,String nickName);

    @Select("SELECT id, user_name, nick_name, status, email, phonenumber, sex, avatar, user_type, create_by, create_time, update_by, update_time, del_flag\n" +
            "FROM sys_user where user_name = #{UserName}")
    public User getUserInfo(String UserName);

    @Select("select count(*) from sys_user where user_name = #{userName}")
    public Integer existUser(String userName);
}
