package com.clonecoding.velogclone_be.controller;

import com.clonecoding.velogclone_be.dto.SignupRequestDto;
import com.clonecoding.velogclone_be.dto.UserResponseDto;
import com.clonecoding.velogclone_be.model.User;
import com.clonecoding.velogclone_be.repository.UserRepository;
import com.clonecoding.velogclone_be.security.UserDetailsImpl;
import com.clonecoding.velogclone_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class
UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    //회원가입
    @PostMapping("/user/signup")
    public void registerUser(@RequestBody SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
    }


    // 회원 정보 받아오기
    @PostMapping("/islogin")
    public UserResponseDto islogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        System.out.println("username : " + user.getUsername());
        System.out.println("nickname : " + user.getNickname());
        return new UserResponseDto(user.getUsername(), user.getNickname());
    }


    // 유저네임 중복 체크
    @PostMapping( "/user/usernameCheck")
    public Boolean usernameCheck(UserResponseDto requestDto) {
        Optional<User> user = userRepository.findByUsername(requestDto.getUsername());
        return !user.isPresent();

    }

    // 닉네임 중복 체크 및 유효성 검사
    @PostMapping( "/user/nicknameCheck")
    public Boolean nicknameCheck(UserResponseDto requestDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByNickname(requestDto.getNickname()));
        return !user.isPresent();
    }

    // 회원 삭제하기
    @DeleteMapping("/user/{userId}")
    public Long deletUser(@PathVariable Long userId){
        User user =  userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("모임이 존재하지 않습니다. ")
        );
        userRepository.deleteById(userId);
        return userId;
    }
}



