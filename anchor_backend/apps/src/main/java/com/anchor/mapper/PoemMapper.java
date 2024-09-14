package com.anchor.mapper;

import com.anchor.domain.Poem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PoemMapper extends BaseMapper<Poem> {

    // 随机获取一条数据
    @Select("SELECT * FROM `anchor`.`poems`\n" +
            "ORDER BY RAND()\n" +
            "LIMIT 1;")
    Poem getRandomPoem();

    //保存诗词
    @Insert("INSERT INTO `anchor`.`poems` (`verse`, `author`, `poem_title`) VALUES (#{verse}, #{author}, #{poem_title});")
    Integer savePoem(String verse, String author, String poem_title);

}
