package com.clonecoding.velogclone_be.dto.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentResponseDto {
    private Long postingId;
    private Long commentId;
    private String nickname;
    private String profileImage;
    private String comment;
    private String createdAtComment;
}
