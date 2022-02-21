package com.clonecoding.velogclone_be.service;

import com.clonecoding.velogclone_be.dto.*;
import com.clonecoding.velogclone_be.model.Article;
import com.clonecoding.velogclone_be.model.ArticleTag;
import com.clonecoding.velogclone_be.model.Image;
import com.clonecoding.velogclone_be.model.Tag;
import com.clonecoding.velogclone_be.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    // 게시글 생성
    public ArticleResponseDto creatArticle(ArticleRequestDto requestDto) {
        Article article = new Article(requestDto);

        List<ArticleTag> tags = creatTags(requestDto, article);
        article.setTags(tags);
        List<Image> images = new ArrayList<>();
        if(requestDto.getImageFiles() == null){
            images = null;
        }else {
            List<String> imageFiles = new ArrayList<>();
            for (int i = 0; i < requestDto.getImageFiles().size(); i++) {
                String imageFile = requestDto.getImageFiles().get(i);
                Image image = new Image();
                image.setImageFile(imageFile);
                imageFiles.add(imageFile);
                Image saveImage = imageRepository.save(image);
                images.add(saveImage);
            }
        }
        article.setImageFiles(images);
        Article saveArticle = articleRepository.save(article);


        //태그 다시 빼기
        List<String> responseTags = new ArrayList<>();
        for(int i = 0; i < saveArticle.getTags().size(); i++){
            String tagName = saveArticle.getTags().get(i).getTag().getTagName();
            responseTags.add(tagName);
        }

        List<String> responseImages = new ArrayList<>();
        if(saveArticle.getImageFiles() == null){
            responseImages = null;
        }else {
            for (int i = 0; i < saveArticle.getImageFiles().size(); i++) {
                String imageFile = saveArticle.getImageFiles().get(i).getImageFile();
                responseImages.add(imageFile);
            }
        }

        ArticleResponseDto articleResponseDto = new ArticleResponseDto(saveArticle);
        articleResponseDto.setImageFiles(responseImages);
        articleResponseDto.setTags(responseTags);

        return articleResponseDto;
    }

    // 태그 생성
    private List<ArticleTag> creatTags(ArticleRequestDto requestDto, Article article) {
        List<ArticleTag> tags = new ArrayList<>();
        for(int i = 0; i < requestDto.getTag().size(); i++){
            Tag tag = new Tag();
            tag.setTagName(requestDto.getTag().get(i));
            Tag saveTag = tagRepository.save(tag);
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticle(article);
            articleTag.setTag(saveTag);
            articleTagRepository.save(articleTag);
            tags.add(articleTag);
        }
        return tags;
    }

    // 게시글 수정
    public ArticleResponseDto editArticle(Long postingId, ArticleRequestDto requestDto) {
        Article foundArticle = articleRepository.findById(postingId).orElseThrow(
                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );
        foundArticle.setTitle(requestDto.getTitle());
        foundArticle.setContent(requestDto.getContent());

        Article saveArticle = articleRepository.save(foundArticle);

        List<String> responseTags = new ArrayList<>();
        for(int i = 0; i < saveArticle.getTags().size(); i++){
            String tagName = saveArticle.getTags().get(i).getTag().getTagName();
            responseTags.add(tagName);
        }

        List<String> responseImages = new ArrayList<>();
        for(int i = 0; i < saveArticle.getImageFiles().size(); i++){
            String imageFile = saveArticle.getImageFiles().get(i).getImageFile();
            responseImages.add(imageFile);
        }

        ArticleResponseDto articleResponseDto = new ArticleResponseDto(saveArticle);
        articleResponseDto.setImageFiles(responseImages);
        articleResponseDto.setTags(responseTags);

        return articleResponseDto;
    }

    // 게시글 삭제
    public HashMap<String, Long> deleteArticle(Long postingId) {
        Article foundArticle = articleRepository.findById(postingId).orElseThrow(
                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );

        HashMap<String, Long> responseId = new HashMap<>();
        responseId.put("postingId", foundArticle.getId());

        articleRepository.delete(foundArticle);

        return responseId;
    }

    // 게시글 상세조회
    public DetailArticleResponseDto getArticle(Long postingId) {
        Article foundArticle = articleRepository.findById(postingId).orElseThrow(
                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );

        List<String> responseTags = new ArrayList<>();
        for (int i = 0; i < foundArticle.getTags().size(); i++) {
            String tagName = foundArticle.getTags().get(i).getTag().getTagName();
            responseTags.add(tagName);
        }

        List<String> responseImages = new ArrayList<>();
        for(int i = 0; i < foundArticle.getImageFiles().size(); i++){
            String imageFile = foundArticle.getImageFiles().get(i).getImageFile();
            responseImages.add(imageFile);
        }

        String profileImage = "";
        if(userRepository.findByNickname(foundArticle.getNickname()).getImgUrl().isEmpty()){
            profileImage = null;
        }else {
            profileImage = userRepository.findByNickname(foundArticle.getNickname()).getImgUrl();
        }

        LocalDate currentDateTime = LocalDate.from(LocalDateTime.now());
        LocalDate articleTime = LocalDate.from(foundArticle.getCreatedAt());
        Period period = Period.between(currentDateTime, articleTime);
        String dayBefore = "";
        dayBefore += period.getDays();
        dayBefore += "일 전";


        DetailArticleResponseDto detailArticleResponseDto = new DetailArticleResponseDto(foundArticle);
        detailArticleResponseDto.setTags(responseTags);
        detailArticleResponseDto.setImageFiles(responseImages);
        detailArticleResponseDto.setProfileImage(profileImage);
        detailArticleResponseDto.setDayBefore(dayBefore);
        detailArticleResponseDto.setCommentCnt(foundArticle.getComments().size());
        detailArticleResponseDto.setLike(foundArticle.getLikes().size());
        detailArticleResponseDto.setModifiedAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(foundArticle.getModifiedAt()));
        return detailArticleResponseDto;
    }

    // 게시글 전체조회(최신순)
    public AllArticleResponseDto recentArticles() {
        List<Article> articleList = articleRepository.findAllByOrderByCreatedAtDesc();

        List<ArticlesResponseDto> articlesResponseDtoList = new ArrayList<>();
        for(int i = 0; i < articleList.size(); i++) {
            // 리스트안에 담아줄 객체 생성
            ArticlesResponseDto responseDto = new ArticlesResponseDto(articleList.get(i));
            // 생성일자 몇일전인지 비교
            LocalDate currentDateTime = LocalDate.from(LocalDateTime.now());
            LocalDate articleTime = LocalDate.from(articleList.get(i).getCreatedAt());
            Period period = Period.between(currentDateTime, articleTime);
            String dayBefore = "";
            dayBefore += period.getDays();
            dayBefore += "일 전";
            responseDto.setDayBefore(dayBefore);
            // 태그 빼서 리스트에 넣기
            List<String> responseTags = new ArrayList<>();
            for(int j = 0; j < articleList.get(i).getTags().size(); j++){
                String tagName = articleList.get(i).getTags().get(j).getTag().getTagName();
                responseTags.add(tagName);
            }
            responseDto.setTag(responseTags);
            // 썸네일 이미지 빼기
            String thumnail = "";
            if(articleList.get(i).getImageFiles().size() == 0){
                thumnail = null;
            }else {
                thumnail = articleList.get(i).getImageFiles().get(0).getImageFile();
            }
            responseDto.setThumnail(thumnail);
            // 유저 프로필이미지
            String profileImage = "";
            if(userRepository.findByNickname(articleList.get(i).getNickname()).getImgUrl() == null){
                profileImage = null;
            }else {
                profileImage = userRepository.findByNickname(articleList.get(i).getNickname()).getImgUrl();
            }
            responseDto.setProfileImage(profileImage);
            responseDto.setCommentCnt(articleList.get(i).getComments().size());
            responseDto.setLike(articleList.get(i).getLikes().size());
            articlesResponseDtoList.add(responseDto);
        }

        AllArticleResponseDto articles = new AllArticleResponseDto();
        articles.setArticles(articlesResponseDtoList);

        return articles;
    }

    // 게시글 전체조회(트렌드, 옵션별)
    public AllArticleResponseDto trendArticles(String options) {
        List<Article> articleList = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println(currentDateTime);
        if (options.equals("today")) {
            System.out.println(currentDateTime.minusHours(24));
            articleList = articleRepository.findAllByCreatedAtBetweenOrderByLikesDesc(currentDateTime.minusHours(24), LocalDateTime.now());
        }
        if (options.equals("week")) {
            System.out.println(currentDateTime.minusWeeks(1));
            articleList = articleRepository.findAllByCreatedAtBetweenOrderByLikesDesc(currentDateTime.minusWeeks(1), LocalDateTime.now());
        }
        if (options.equals("month")) {
            System.out.println(currentDateTime.minusMonths(1));
            articleList = articleRepository.findAllByCreatedAtBetweenOrderByLikesDesc(currentDateTime.minusMonths(1), LocalDateTime.now());
        }

        List<ArticlesResponseDto> articlesResponseDtoList = new ArrayList<>();
        for(int i = 0; i < articleList.size(); i++) {
            // 리스트안에 담아줄 객체 생성
            ArticlesResponseDto responseDto = new ArticlesResponseDto(articleList.get(i));
            // 생성일자 몇일전인지 비교
            LocalDate nowTime = LocalDate.from(LocalDateTime.now());
            LocalDate articleTime = LocalDate.from(articleList.get(i).getCreatedAt());
            Period period = Period.between(nowTime, articleTime);
            String dayBefore = "";
            dayBefore += period.getDays();
            dayBefore += "일 전";
            responseDto.setDayBefore(dayBefore);
            // 태그 빼서 리스트에 넣기
            List<String> responseTags = new ArrayList<>();
            for(int j = 0; j < articleList.get(i).getTags().size(); j++){
                String tagName = articleList.get(i).getTags().get(j).getTag().getTagName();
                responseTags.add(tagName);
            }
            responseDto.setTag(responseTags);
            // 썸네일 이미지 빼기
            String thumnail = "";
            if(articleList.get(i).getImageFiles().size() == 0){
                thumnail = null;
            }else {
                thumnail = articleList.get(i).getImageFiles().get(0).getImageFile();
            }
            responseDto.setThumnail(thumnail);
            // 유저 프로필이미지
            String profileImage = "";
            if(userRepository.findByNickname(articleList.get(i).getNickname()).getImgUrl() == null){
                profileImage = null;
            }else {
                profileImage = userRepository.findByNickname(articleList.get(i).getNickname()).getImgUrl();
            }
            responseDto.setProfileImage(profileImage);
            responseDto.setCommentCnt(articleList.get(i).getComments().size());
            responseDto.setLike(articleList.get(i).getLikes().size());
            articlesResponseDtoList.add(responseDto);
        }

        AllArticleResponseDto articles = new AllArticleResponseDto();
        articles.setArticles(articlesResponseDtoList);

        return articles;
    }
}
