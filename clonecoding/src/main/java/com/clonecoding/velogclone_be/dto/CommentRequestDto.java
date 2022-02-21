package com.clonecoding.velogclone_be.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String nickname;
    private String comment;
    private Long postingId;
}
