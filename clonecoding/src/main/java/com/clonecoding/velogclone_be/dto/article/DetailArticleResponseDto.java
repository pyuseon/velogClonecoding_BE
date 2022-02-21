package com.clonecoding.velogclone_be.dto.article;

import com.clonecoding.velogclone_be.model.Article;
import com.clonecoding.velogclone_be.model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DetailArticleResponseDto {
    private Long postingId;
    private String title;
    private String content;
    private String nickName;
//    private String imageFile;
    private List<String> tags;
    private List<Comment> comments;
    private String modifiedAt;

    public DetailArticleResponseDto(Article article) {
        this.postingId = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.nickName = article.getNickName();
        this.comments = article.getComments();
    }
}