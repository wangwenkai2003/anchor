package com.anchor.controller.poem;

import com.anchor.domain.Poem;
import com.anchor.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/poem")
public class PoemController {
    @Autowired
    PoemService poemService;

    //获取随机诗歌
@GetMapping("getRandomPoem")
    private ResponseEntity getPoem(){
        //调用poemService的getRandomPoem方法获取随机诗歌
        Poem randomPoem = poemService.getRandomPoem();
        //返回随机诗歌
        return randomPoem!=null ? ResponseEntity.ok(randomPoem):ResponseEntity.status(500).body("获取随机诗歌失败");
    }

//保存诗歌
@PostMapping("savePoem")
    private ResponseEntity savePoem(@RequestBody Poem poem){
        //调用poemService的savePoem方法保存诗歌，传入诗歌的verse、author和poemTitle
        Integer save = poemService.savePoem(poem.getVerse(), poem.getAuthor(), poem.getAuthor());
        //根据保存结果返回保存成功或失败
        return save>0?ResponseEntity.ok("保存成功"):ResponseEntity.status(500).body("保存失败");
    }
}
