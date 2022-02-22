package com.clonecoding.velogclone_be.controller;

import com.clonecoding.velogclone_be.dto.article.AllArticleResponseDto;
import com.clonecoding.velogclone_be.dto.article.ArticleRequestDto;
import com.clonecoding.velogclone_be.dto.article.ArticleResponseDto;
import com.clonecoding.velogclone_be.dto.article.DetailArticleResponseDto;
import com.clonecoding.velogclone_be.security.UserDetailsImpl;
import com.clonecoding.velogclone_be.service.ArticleService;
import com.clonecoding.velogclone_be.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final S3Uploader s3Uploader;

    // 게시글 생성
    @PostMapping("/api/posting")
    public ArticleResponseDto creatArticle(@RequestBody ArticleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return articleService.creatArticle(requestDto, userDetails);
    }

    // 게시글 수정
    @PutMapping("/api/posting/{postingId}")
    public ArticleResponseDto editArticle(@PathVariable Long postingId, @RequestBody ArticleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return articleService.editArticle(postingId, requestDto, userDetails);
    }

    // 게시글 삭제
    @DeleteMapping("/api/posting/{postingId}")
    public HashMap<String, Long> deleteArticle(@PathVariable Long postingId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return articleService.deleteArticle(postingId, userDetails);
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


    // 게시글 이미지 업로드
    @PostMapping("/api/posting/image")
    public String uploadImages( @RequestParam(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        String imgUrl = null;
        if(!multipartFile.isEmpty()){
            imgUrl = s3Uploader.upload(multipartFile, "static");
        }
        return imgUrl;
    }

}
