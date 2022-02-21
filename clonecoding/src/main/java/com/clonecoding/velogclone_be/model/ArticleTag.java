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

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

}
