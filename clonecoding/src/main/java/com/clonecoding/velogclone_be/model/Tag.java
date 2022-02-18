package com.clonecoding.velogclone_be.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Tag {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column
    private String tagName;

    @JsonManagedReference
    @OneToMany(mappedBy = "tag", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ArticleTag> articles;
}
