package com.clonecoding.velogclone_be.controller;

import com.clonecoding.velogclone_be.dto.LikesRequestDto;
import com.clonecoding.velogclone_be.model.Article;
import com.clonecoding.velogclone_be.model.Likes;
import com.clonecoding.velogclone_be.model.User;
import com.clonecoding.velogclone_be.repository.ArticleRepository;
import com.clonecoding.velogclone_be.repository.LikesRepository;
import com.clonecoding.velogclone_be.repository.UserRepository;
import com.clonecoding.velogclone_be.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


@RestController
@RequiredArgsConstructor
public class LikesController {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final LikesRepository likesRepository;
    private final LikesService likesService;

    // 좋아요
    @PostMapping("/api/like")
    public String creatLikes(@RequestBody LikesRequestDto requestDto){
        return likesService.creatLikes(requestDto);
    }


    // 좋아요 취소
    @DeleteMapping("/api/unlike")
    public Long deleteLikes(@RequestBody LikesRequestDto requestDto) {
        return likesService.deleteLikes(requestDto);
    }

}
