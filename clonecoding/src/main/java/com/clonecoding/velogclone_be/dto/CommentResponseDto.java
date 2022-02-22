package com.clonecoding.velogclone_be.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentResponseDto {
    private Long postingId;
    private Long commentId;
    private String nickname;
    private String profileImage;
    private String comment;
    private String createdAtComment;
}
