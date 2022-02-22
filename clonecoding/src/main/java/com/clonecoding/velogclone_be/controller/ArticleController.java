package com.clonecoding.velogclone_be.controller;

import com.clonecoding.velogclone_be.dto.article.*;
import com.clonecoding.velogclone_be.service.ArticleService;
import com.clonecoding.velogclone_be.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final S3Uploader s3Uploader;

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

    // 게시글 이미지 업로드
    @PostMapping("/api/posting/image")
    public String uploadImages( @RequestParam(value = "image", required = false) MultipartFile multipartFile) throws IOException {

        String imgUrl = null;
        if(!multipartFile.isEmpty()){
            imgUrl = s3Uploader.upload(multipartFile, "static");
        }
        return imgUrl;
    }

//    // 내가 작성한 게시글 목록 조회
//    @GetMapping("/api/mypage/{nickname}")
//    public ResponseEntity<List<MyArticlesResponseDto>> getMyArticles(@PathVariable String nickname){
//        List<MyArticlesResponseDto> myArticlesResponseDtoList = articleService.getMyArticles(nickname);
//        return ResponseEntity.ok(myArticlesResponseDtoList);
//    }
}
