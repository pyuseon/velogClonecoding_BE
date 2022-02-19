package com.clonecoding.velogclone_be.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String imgUrl;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Likes> likes;


    public User(String username, String nickname, String enPassword, String imgUrl) {
        this.username = username;
        this.nickname = nickname;
        this.password = enPassword;
        this.imgUrl = imgUrl;
    }
}
