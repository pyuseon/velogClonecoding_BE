package com.clonecoding.velogclone_be.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentResponseDto {
    private String msg;
    private Long postingId;
    private Long commentId;
    private String nickname;
    private String profileImage;
    private String comment;
    private String createdAtComment;
}
