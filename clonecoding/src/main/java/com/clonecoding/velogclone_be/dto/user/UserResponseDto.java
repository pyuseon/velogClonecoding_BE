package com.clonecoding.velogclone_be.dto.user;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String username;
    private String nickname;
    private String imgUrl;
    private List<Long> userLikes;

}