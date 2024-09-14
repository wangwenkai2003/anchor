package com.anchor.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;


@Data
@TableName("Poems")
public class Poem {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String verse;

    private String author;

    private String poemTitle;

}