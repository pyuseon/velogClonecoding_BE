package com.clonecoding.velogclone_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesRequestDto {
    private String nickname;
    private Long postingId;
}