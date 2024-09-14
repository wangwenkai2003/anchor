package com.anchor.service.impl;

import com.anchor.domain.Poem;
import com.anchor.mapper.PoemMapper;
import com.anchor.service.PoemService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PoemServiceImpl extends ServiceImpl<PoemMapper,Poem> implements PoemService {
    @Autowired
    private PoemMapper poemMapper;
    @Override
    public Poem getRandomPoem() {
        Poem poem = poemMapper.getRandomPoem();
        return poem;
    }

    @Override
    public Integer savePoem(String verse, String author, String poem_title) {
        return poemMapper.savePoem(verse,author,poem_title);
    }
}
