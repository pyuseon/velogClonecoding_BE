package com.clonecoding.velogclone_be.model;

import com.clonecoding.velogclone_be.dto.article.ArticleRequestDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Article extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String nickname;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> imageFiles;

    @JsonManagedReference
    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ArticleTag> tags;

    @JsonManagedReference
    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @JsonManagedReference
    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Likes> likes;

    public Article(ArticleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.nickname = requestDto.getNickname();
    }
}