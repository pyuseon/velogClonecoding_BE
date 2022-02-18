package com.clonecoding.velogclone_be.service;

import com.clonecoding.velogclone_be.dto.ArticleRequestDto;
import com.clonecoding.velogclone_be.dto.ArticleResponseDto;
import com.clonecoding.velogclone_be.model.Article;
import com.clonecoding.velogclone_be.model.Tag;
import com.clonecoding.velogclone_be.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final TagRepository tagRepository;

    public ArticleResponseDto creatArticle(ArticleRequestDto requestDto) {
        Article article = new Article(requestDto);
        requestDto.getTag();
        for(int i = 0; i < requestDto.getTag().size(); i++){
            Tag tag = tagRepository.getById(requestDto.getTag().get(i));

        }
    }
}
