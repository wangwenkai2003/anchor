package com.anchor.service;

import com.anchor.domain.Poem;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PoemService extends IService<Poem> {
    Poem getRandomPoem();
    Integer savePoem(String verse, String author, String poem_title);
}
