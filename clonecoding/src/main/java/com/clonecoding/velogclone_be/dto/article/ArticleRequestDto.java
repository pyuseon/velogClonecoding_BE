package com.clonecoding.velogclone_be.dto.article;

import com.clonecoding.velogclone_be.model.Image;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ArticleRequestDto {
    private String title;
    private String content;
    private List<String> imageFiles;
    private String nickname;
    private List<String> tag;
}
