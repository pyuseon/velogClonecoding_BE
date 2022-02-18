package com.clonecoding.velogclone_be.controller;

import com.clonecoding.velogclone_be.dto.ArticleRequestDto;
import com.clonecoding.velogclone_be.dto.ArticleResponseDto;
import com.clonecoding.velogclone_be.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/api/posting")
    public ArticleResponseDto creatArticle(@RequestBody ArticleRequestDto requestDto){
        return articleService.creatArticle(requestDto);
    }
}
