package com.clonecoding.velogclone_be.model;

import com.clonecoding.velogclone_be.dto.article.ArticleRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long likeId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;


    public Likes(User user, Article ariticle) {
        this.user = user;
        this.article = ariticle;

    }
}
