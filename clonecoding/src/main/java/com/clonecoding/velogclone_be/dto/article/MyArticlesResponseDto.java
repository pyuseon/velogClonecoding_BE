package com.clonecoding.velogclone_be.dto.article;


import com.clonecoding.velogclone_be.model.Article;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class MyArticlesResponseDto {
    private Long postingId;
    private String thumnail;
    private String title;
    private String content;
    private String dayBefore;
    private int commentCnt;
    private List<String> tag;

//    public MyArticlesResponseDto(Article article){
//        this.postingId = article.getId();
//        this.title = article.getTitle();
//        this.content = article.getContent();
//
//    }
}
