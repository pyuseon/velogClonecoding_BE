package com.clonecoding.velogclone_be.controller;

import com.clonecoding.velogclone_be.dto.SignupRequestDto;
import com.clonecoding.velogclone_be.dto.UserResponseDto;
import com.clonecoding.velogclone_be.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


//@RestController
//@RequiredArgsConstructor
//public class
//UserController {
//
//    private final UserService userService;
//    private final UserRepository userRepository;

//    //회원가입
//    @PostMapping("/user/signup")
//    public void registerUser(@RequestBody SignupRequestDto requestDto) {
//        userService.registerUser(requestDto);
//    }
//
//
//    @PostMapping("/islogin")
//    public UserResponseDto islogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
//        System.out.println("username : " + user.getUsername());
//        return new UserResponseDto(user.getUsername());
//    }
//
//
//    // 모임 삭제하기
//    @DeleteMapping("/user/{userId}")
//    public Long deletUser(@PathVariable Long userId){
//        User user =  userRepository.findById(userId).orElseThrow(
//                () -> new IllegalArgumentException("모임이 존재하지 않습니다. ")
//        );
//        userRepository.deleteById(userId);
//        return userId;
//    }
//}



