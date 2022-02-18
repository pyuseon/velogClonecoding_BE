package com.clonecoding.velogclone_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ArticleResponseDto {
    private Long postingId;
    private String title;
    private String content;
    private String nickName;
    private String imageFile;
    private List<String> tags;
}
