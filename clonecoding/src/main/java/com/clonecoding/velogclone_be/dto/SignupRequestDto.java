package com.clonecoding.velogclone_be.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequestDto {
    // 이메일
    private String username;
    // 닉네임
    private String nickname;
    private String password;
//    private String checkPassword;
}
