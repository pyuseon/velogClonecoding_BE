package com.clonecoding.velogclone_be.service;


import com.clonecoding.velogclone_be.dto.LikesRequestDto;
import com.clonecoding.velogclone_be.model.Article;
import com.clonecoding.velogclone_be.model.Likes;
import com.clonecoding.velogclone_be.model.User;
import com.clonecoding.velogclone_be.repository.ArticleRepository;
import com.clonecoding.velogclone_be.repository.LikesRepository;
import com.clonecoding.velogclone_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final LikesRepository likesRepository;

    public String creatLikes(LikesRequestDto requestDto){
        User user = userRepository.findByNickname(requestDto.getNickname());
        Article article = articleRepository.findById(requestDto.getPostingId()).orElseThrow(
                () -> new IllegalArgumentException("포스팅이 존재하지 않습니다. ")
        );
        Likes likes = new Likes(user, article);
        likesRepository.save(likes);

        return  "{\"response\":\"true\"}";
    }


    public Long deleteLikes(LikesRequestDto requestDto){
        User user = userRepository.findByNickname(requestDto.getNickname());
        Article article = articleRepository.findById(requestDto.getPostingId()).orElseThrow(
                () -> new IllegalArgumentException("포스팅이 존재하지 않습니다. ")
        );

        Long deleteUserId = user.getId();
        Long deleteAriticleId = article.getId();

        List<Likes> likesList= likesRepository.findAllByUserIdAndArticleId(deleteUserId, deleteAriticleId);
        System.out.println(likesList.get(0).getLikeId());

        Long deleteLikesId = likesList.get(0).getLikeId();
        likesRepository.deleteById(deleteLikesId);

        return  deleteLikesId;
    }

}
