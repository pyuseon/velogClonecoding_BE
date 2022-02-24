package com.clonecoding.velogclone_be.controller;

import com.clonecoding.velogclone_be.dto.user.SignupRequestDto;
import com.clonecoding.velogclone_be.dto.user.UserResponseDto;
import com.clonecoding.velogclone_be.model.Likes;
import com.clonecoding.velogclone_be.model.User;
import com.clonecoding.velogclone_be.repository.UserRepository;
import com.clonecoding.velogclone_be.security.UserDetailsImpl;
import com.clonecoding.velogclone_be.service.S3Uploader;
import com.clonecoding.velogclone_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.security.auth.message.MessagePolicy;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    @PostMapping("/user/signup")
    public String registerUser(@RequestParam("username") String username,
            @RequestParam("nickname") String nickname,
            @RequestParam("password") String password,
            @RequestParam(value = "profileImage", required = false) MultipartFile multipartFile
                               ) throws IOException {

        System.out.println(multipartFile);
        SignupRequestDto requestDto = new SignupRequestDto(username, nickname, password);


        // 기본 이미지
        String imgUrl = "https://bookcafe-bucket.s3.ap-northeast-2.amazonaws.com/signup/ca0d237c-6f48-42a2-a04b-bd999ea3b9f5noImage.png";
        // 프로필 업로드 시 프로필 이미지로 업로드
        if(multipartFile != null && !multipartFile.isEmpty()){
            imgUrl = s3Uploader.upload(multipartFile, "signup");
        }
        System.out.println(imgUrl);

        userService.registerUser(requestDto, imgUrl);
        return "회원가입 성공!";
    }

    // 회원 정보 받아오기
    @PostMapping("/islogin")
    public UserResponseDto islogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        System.out.println("username : " + user.getUsername());
        System.out.println("nickname : " + user.getNickname());
        System.out.println(user.getImgUrl());
        System.out.println(user.getLikes().size());
        return userService.getInfo(user);
    }


    // 유저네임 중복 체크
    @PostMapping( "/user/usernameCheck")
    public Boolean usernameCheck(@RequestBody  UserResponseDto requestDto) {
        System.out.println(requestDto.getUsername());
        Optional<User> user = userRepository.findByUsername(requestDto.getUsername());
        return !user.isPresent();
    }

    // 닉네임 중복 체크 및 유효성 검사
    @PostMapping( "/user/nicknameCheck")
    public Boolean nicknameCheck(@RequestBody UserResponseDto requestDto) {
        System.out.println(requestDto.getNickname());
        Optional<User> user = Optional.ofNullable(userRepository.findByNickname(requestDto.getNickname()));
        return !user.isPresent();
    }

    // 회원 삭제하기
    @DeleteMapping("/user/{userId}")
    public Long deletUser(@PathVariable Long userId) throws UnsupportedEncodingException {
        User user =  userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("모임이 존재하지 않습니다. ")
        );
        if(!Objects.equals(user.getImgUrl(), "https://bookcafe-bucket.s3.ap-northeast-2.amazonaws.com/signup/ca0d237c-6f48-42a2-a04b-bd999ea3b9f5noImage.png")){
            s3Uploader.deleteS3(user.getImgUrl());
        }
        userRepository.deleteById(userId);
        return userId;
    }
}



