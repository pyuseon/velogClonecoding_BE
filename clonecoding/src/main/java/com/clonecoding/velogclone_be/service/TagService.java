package com.clonecoding.velogclone_be.service;

import com.clonecoding.velogclone_be.dto.TagSearchResponseDto;
import com.clonecoding.velogclone_be.model.Article;
import com.clonecoding.velogclone_be.model.ArticleTag;
import com.clonecoding.velogclone_be.model.Tag;
import com.clonecoding.velogclone_be.model.User;
import com.clonecoding.velogclone_be.repository.ArticleRepository;
import com.clonecoding.velogclone_be.repository.ArticleTagRepository;
import com.clonecoding.velogclone_be.repository.TagRepository;
import com.clonecoding.velogclone_be.repository.UserRepository;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<TagSearchResponseDto> searchTag(String tagName) {
        List<TagSearchResponseDto> tagSearchResponseDtoList = new ArrayList<>();

        List<Tag> searchTagNames = tagRepository.findByTagName(tagName);
        if(searchTagNames == null || searchTagNames.size() == 0) {
            return tagSearchResponseDtoList;
        }

        for (Tag searchTagName : searchTagNames) {

            List<ArticleTag> articleTags = articleTagRepository.findAllByTag(searchTagName);
            for(ArticleTag eachArticleTag : articleTags){
                Article article = articleRepository.findById(eachArticleTag.getArticle().getId()).orElseThrow(
                        () -> new RuntimeException("태그는 있지만 해당하는 게시글이 없습니다.")
                );

                String thumnail = "";
                if(article.getImageFiles().size() == 0){
                    thumnail = null;
                }else {
                    thumnail = article.getImageFiles().get(0).getImageFile();
                }

                LocalDate currentDateTime = LocalDate.from(LocalDateTime.now());
                LocalDate articleTime = LocalDate.from(article.getCreatedAt());
                Period period = Period.between(currentDateTime, articleTime);
                String dayBefore = "";
                int days = (period.getDays())*-1;
                if(days < 1){
                    LocalDateTime nowTime = LocalDateTime.now();
                    LocalDateTime createdTime = article.getCreatedAt();
                    Duration duration = Duration.between(nowTime, createdTime);
                    int time = (int) duration.getSeconds();
                    dayBefore += (time/3600)*-1;
                    dayBefore += "시간 전";
                } else {
                    dayBefore += days;
                    dayBefore += "일 전";
                }

                String profileImage = null;
                User findUser = userRepository.findByNickname(article.getNickname());
                if(findUser.getImgUrl() != null){
                    profileImage = findUser.getImgUrl();
                }

                //Tag
                List<ArticleTag> searchArticleTags = articleTagRepository.findAllByArticle_Id(article.getId());
                List<String> tagNameList = new ArrayList<>();
                for (ArticleTag eachArticleTags : searchArticleTags){
                    Long tagId = eachArticleTags.getTag().getId();
                    Optional<Tag> tagTemp = tagRepository.findById(tagId);
                    String searchTag = tagTemp.get().getTagName();
                    tagNameList.add(searchTag);
                }

                TagSearchResponseDto tagSearchResponseDto = new TagSearchResponseDto();
                tagSearchResponseDto.setPostingId(article.getId());
                tagSearchResponseDto.setTitle(article.getTitle());
                tagSearchResponseDto.setContent(article.getContent());
                tagSearchResponseDto.setNickname(article.getNickname());
                tagSearchResponseDto.setCommentCnt(article.getComments().size());
                tagSearchResponseDto.setThumnail(thumnail);
                tagSearchResponseDto.setProfileImage(profileImage);
                tagSearchResponseDto.setDayBefore(dayBefore);
                tagSearchResponseDto.setTag(tagNameList);
                tagSearchResponseDtoList.add(tagSearchResponseDto);
            }
        }

        return tagSearchResponseDtoList;
    }
}
