package com.clonecoding.velogclone_be.dto.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreatCommentResponseDto {
    private String msg;
    private Long commentId;
    private String createdAtComment;
}
