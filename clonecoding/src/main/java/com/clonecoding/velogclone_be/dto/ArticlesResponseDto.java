package com.clonecoding.velogclone_be.dto;

import com.clonecoding.velogclone_be.model.Article;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ArticlesResponseDto {
    private Long postingId;
    private String thumnail;
    private String title;
    private String content;
    private String dayBefore;
    private int commentCnt;
    private String profileImage;
    private String nickname;
    private List<String> tag;
    private int like;

    public ArticlesResponseDto(Article article) {
        this.postingId = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.nickname = article.getNickname();
    }
}
