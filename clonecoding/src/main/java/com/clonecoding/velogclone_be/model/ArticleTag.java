package com.clonecoding.velogclone_be.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class ArticleTag {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long articleTagId;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

}
