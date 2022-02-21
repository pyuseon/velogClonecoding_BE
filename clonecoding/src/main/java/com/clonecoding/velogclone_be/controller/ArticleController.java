package com.clonecoding.velogclone_be.controller;

import com.clonecoding.velogclone_be.dto.AllArticleResponseDto;
import com.clonecoding.velogclone_be.dto.ArticleRequestDto;
import com.clonecoding.velogclone_be.dto.ArticleResponseDto;
import com.clonecoding.velogclone_be.dto.DetailArticleResponseDto;
import com.clonecoding.velogclone_be.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    // 게시글 생성
    @PostMapping("/api/posting")
    public ArticleResponseDto creatArticle(@RequestBody ArticleRequestDto requestDto){
        return articleService.creatArticle(requestDto);
    }

    // 게시글 수정
    @PutMapping("/api/posting/{postingId}")
    public ArticleResponseDto editArticle(@PathVariable Long postingId, @RequestBody ArticleRequestDto requestDto){
        return articleService.editArticle(postingId, requestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/api/posting/{postingId}")
    public HashMap<String, Long> deleteArticle(@PathVariable Long postingId){
        return articleService.deleteArticle(postingId);
    }

    // 게시글 상세조회
    @GetMapping("/api/posting/{postingId}")
    public DetailArticleResponseDto getArticle(@PathVariable Long postingId){
        return articleService.getArticle(postingId);
    }

    // 게시글 전체조회(최신순)
    @GetMapping("/api/posting")
    public AllArticleResponseDto recentArticles(){
        return articleService.recentArticles();
    }

    // 게시글 전체조회(트렌드, 옵션별)
    @GetMapping("/api/posting/likes/{options}")
    public AllArticleResponseDto trendArticles(@PathVariable String options){
        return articleService.trendArticles(options);
    }

}
