package com.clonecoding.velogclone_be.dto;

import com.clonecoding.velogclone_be.model.Article;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AllArticleResponseDto {
    private List<ArticlesResponseDto> articles;
}
