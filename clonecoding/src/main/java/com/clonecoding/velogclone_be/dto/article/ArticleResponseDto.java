package com.clonecoding.velogclone_be.dto.article;

import com.clonecoding.velogclone_be.model.Article;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ArticleResponseDto {
    private Long postingId;
    private String title;
    private String content;
    private String nickname;
    private List<String > imageFiles;
    private List<String> tags;

    public ArticleResponseDto(Article article){
        this.postingId = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.nickname = article.getNickname();
    }
}
