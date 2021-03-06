package com.clonecoding.velogclone_be.dto.article;

import com.clonecoding.velogclone_be.dto.comment.CommentResponseDto;
import com.clonecoding.velogclone_be.model.Article;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DetailArticleResponseDto {
    private Long postingId;
    private String title;
    private String content;
    private String thumnail;
    private String nickname;
    private String username;
    private List<String> imageFiles;
    private String profileImage;
    private List<String> tags;
    private List<CommentResponseDto> commentList;
    private String modifiedAt;
    private String dayBefore;
    private int commentCnt;
    private int like;

    public DetailArticleResponseDto(Article article) {
        this.postingId = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.nickname = article.getNickname();
    }
}
