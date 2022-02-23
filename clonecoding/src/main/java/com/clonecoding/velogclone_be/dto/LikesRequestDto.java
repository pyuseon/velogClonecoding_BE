package com.clonecoding.velogclone_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikesRequestDto {
    private Long postingId;
    private String nickname;

}
