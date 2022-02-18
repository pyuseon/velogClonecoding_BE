package com.clonecoding.velogclone_be.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String username;
    private String nickname;
    private String password;
    private String checkPassword;
}
