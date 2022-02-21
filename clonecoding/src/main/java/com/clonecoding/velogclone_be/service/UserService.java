package com.clonecoding.velogclone_be.service;

import com.clonecoding.velogclone_be.dto.user.SignupRequestDto;
import com.clonecoding.velogclone_be.model.User;
import com.clonecoding.velogclone_be.repository.UserRepository;;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(SignupRequestDto requestDto, String imgUrl) {

        //중복된 이메일(로그인 id)가 존재할 경우
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }

        //중복된 닉네임이 존재할 경우
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        // 패스워드
        String enPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(username,nickname,enPassword, imgUrl);
        userRepository.save(user); // DB 저장

    }

    public User login(String username, String password) {
        System.out.println(username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("아이디 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password,user.getPassword() ))
        {
            throw new IllegalArgumentException("비밀번호 불일치");
        }
        return user;
    }
}
