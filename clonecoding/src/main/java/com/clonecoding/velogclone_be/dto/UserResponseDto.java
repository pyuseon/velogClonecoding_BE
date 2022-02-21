package com.clonecoding.velogclone_be.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String username;
    private String nickname;
    private String imgUrl;

}