package com.clonecoding.velogclone_be.model;

import com.clonecoding.velogclone_be.dto.ArticleRequestDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
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
    private String userName;


    @Column
    private String imageFile;

    @JsonManagedReference
    @OneToMany(mappedBy = "article", orphanRemoval = true)
    private List<ArticleTag> tags;

    @JsonManagedReference
    @OneToMany(mappedBy = "article", orphanRemoval = true)
    private List<Comment> comments;

    @JsonManagedReference
    @OneToMany(mappedBy = "article", orphanRemoval = true)
    private List<Likes> likes;

    public Article(ArticleRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.userName = requestDto.getUserName();
        this.imageFile = requestDto.getImageFile();
    }
}