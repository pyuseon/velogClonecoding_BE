package com.clonecoding.velogclone_be.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class TagSearchResponseDto {
    private Long postingId;
    private String title;
    private String content;
    private String nickname;
    private String thumnail;
    private String profileImage;
    private String dayBefore;
    private List<String> tag;
    private int commentCnt;
}
