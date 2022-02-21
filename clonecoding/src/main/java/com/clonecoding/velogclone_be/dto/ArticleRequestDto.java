package com.clonecoding.velogclone_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ArticleRequestDto {
    private String title;
    private String content;
    private String imageFile;
    private String nickName;
    private List<String> tag;
}
