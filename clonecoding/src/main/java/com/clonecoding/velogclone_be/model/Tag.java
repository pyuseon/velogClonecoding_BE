package com.clonecoding.velogclone_be.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Tag {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column
    private String tagName;

    @JsonManagedReference
    @OneToMany(mappedBy = "tag", orphanRemoval = true)
    private List<ArticleTag> articles;
}
