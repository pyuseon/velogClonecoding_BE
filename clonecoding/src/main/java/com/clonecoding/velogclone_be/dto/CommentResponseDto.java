package com.clonecoding.velogclone_be.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentResponseDto {
    private String msg;
    private String date;
    private Long commentId;
}
