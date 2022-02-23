package com.clonecoding.velogclone_be.service;

import com.clonecoding.velogclone_be.dto.comment.CommentResponseDto;
import com.clonecoding.velogclone_be.dto.article.*;
import com.clonecoding.velogclone_be.model.*;
import com.clonecoding.velogclone_be.repository.*;
import com.clonecoding.velogclone_be.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    // 게시글 생성
    public ArticleResponseDto creatArticle(ArticleRequestDto requestDto, UserDetailsImpl userDetails) {
        // 로그인 사용자와 게시글 닉네임 비교
        if(!requestDto.getNickname().equals(userDetails.getNickname())){
            throw new IllegalArgumentException("로그인한 사용자의 닉네임과 게시글의 작성자의 닉네임이 맞지 않습니다.");
        }
        // 저장할 article 생성
        Article article = new Article(requestDto);

        // 태그 확인
        List<ArticleTag> tags = new ArrayList<>();
        // 태그가 없다면 null 있다면 태그저장
        if(requestDto.getTag() == null){
            tags = null;
        }else {
            tags = creatTags(requestDto, article);
        }
        article.setTags(tags);

        // 이미지 확인
        List<Image> images = new ArrayList<>();
        // 이미지가 없다면 null 있다면 이미지 저장
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
        // 게시글 저장
        Article saveArticle = articleRepository.save(article);


        // 태그 다시 빼기
        List<String> responseTags = new ArrayList<>();
        for(int i = 0; i < saveArticle.getTags().size(); i++){
            String tagName = saveArticle.getTags().get(i).getTag().getTagName();
            responseTags.add(tagName);
        }
        // 이미지 다시 빼기
        List<String> responseImages = new ArrayList<>();
        if(saveArticle.getImageFiles() == null){
            responseImages = null;
        }else {
            for (int i = 0; i < saveArticle.getImageFiles().size(); i++) {
                String imageFile = saveArticle.getImageFiles().get(i).getImageFile();
                responseImages.add(imageFile);
            }
        }
        // responseDto로 생성해서 반환해주기
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
    public ArticleResponseDto editArticle(Long postingId, ArticleRequestDto requestDto, UserDetailsImpl userDetails) {
        // 게시글 찾기
        Article foundArticle = articleRepository.findById(postingId).orElseThrow(
                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );

        // 로그인한 사용자과 게시글 작성자 확인
        if(!foundArticle.getNickname().equals(userDetails.getNickname())){
            throw new IllegalArgumentException("게시글의 작성자만 수정 할 수 있습니다.");
        }

        // 수정된 제목과 내용 넣어주기
        foundArticle.setTitle(requestDto.getTitle());
        foundArticle.setContent(requestDto.getContent());

        // 수정된 게시글 저장
        Article saveArticle = articleRepository.save(foundArticle);

        // 태그 다시 빼기
        List<String> responseTags = new ArrayList<>();
        for(int i = 0; i < saveArticle.getTags().size(); i++){
            String tagName = saveArticle.getTags().get(i).getTag().getTagName();
            responseTags.add(tagName);
        }

        // 이미지 다시 빼기
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
    public HashMap<String, Long> deleteArticle(Long postingId, UserDetailsImpl userDetails) {
        // 해당 게시글 찾기
        Article foundArticle = articleRepository.findById(postingId).orElseThrow(
                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );

        // 로그인 사용자과 게시글 작성자 확인
        if(!foundArticle.getNickname().equals(userDetails.getNickname())){
            throw new IllegalArgumentException("게시글의 작성자만 삭제 할 수 있습니다.");
        }

        // 반환해줄 id값 만들기
        HashMap<String, Long> responseId = new HashMap<>();
        responseId.put("postingId", foundArticle.getId());

        articleRepository.delete(foundArticle);

        return responseId;
    }

    // 게시글 상세조회
    public DetailArticleResponseDto getArticle(Long postingId) {
        // 게시글 찾기
        Article foundArticle = articleRepository.findById(postingId).orElseThrow(
                () -> new NullPointerException("해당 게시글을 찾을 수 없습니다.")
        );

        // 태그 다시 빼기
        List<String> responseTags = new ArrayList<>();
        for (int i = 0; i < foundArticle.getTags().size(); i++) {
            String tagName = foundArticle.getTags().get(i).getTag().getTagName();
            responseTags.add(tagName);
        }

        // 이미지 다시 빼기
        List<String> responseImages = new ArrayList<>();
        for(int i = 0; i < foundArticle.getImageFiles().size(); i++){
            String imageFile = foundArticle.getImageFiles().get(i).getImageFile();
            responseImages.add(imageFile);
        }

        // 썸네일
        String thumnail = null;
        if(foundArticle.getImageFiles().size() != 0){
            thumnail = foundArticle.getImageFiles().get(0).getImageFile();
        }

        // 프로필 이미지 검사
        String profileImage = null;
        User findUser = userRepository.findByNickname(foundArticle.getNickname());
        if(findUser.getImgUrl() != null){
            profileImage = findUser.getImgUrl();
        }

        // 현재 시간과 생성날짜 비교
        LocalDate currentDateTime = LocalDate.from(LocalDateTime.now());
        LocalDate articleTime = LocalDate.from(foundArticle.getCreatedAt());
        Period period = Period.between(currentDateTime, articleTime);
        String dayBefore = "";
        int days = (period.getDays())*-1;
        if(days < 1){
            LocalDateTime nowTime = LocalDateTime.now();
            LocalDateTime createdTime = foundArticle.getCreatedAt();
            Duration duration = Duration.between(nowTime, createdTime);
            int time = (int) duration.getSeconds();
            dayBefore += (time/3600)*-1;
            dayBefore += "시간 전";
        }else {
            dayBefore += days;
            dayBefore += "일 전";
        }


        // 코멘트빼오기
        List<CommentResponseDto> commentList = new ArrayList<>();
        for(int i = 0; i < foundArticle.getComments().size(); i++){
            CommentResponseDto responseDto = new CommentResponseDto();
            responseDto.setPostingId(foundArticle.getId());
            responseDto.setCommentId(foundArticle.getComments().get(i).getCommentId());
            responseDto.setComment(foundArticle.getComments().get(i).getComment());
            responseDto.setNickname(foundArticle.getComments().get(i).getNickname());
            String commentProfile = null;
            User findUser2 = userRepository.findByNickname(foundArticle.getComments().get(i).getNickname());
            if(findUser2.getImgUrl() != null){
                commentProfile = findUser2.getImgUrl();
            }
            responseDto.setProfileImage(commentProfile);
            responseDto.setCreatedAtComment(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(foundArticle.getComments().get(i).getCreatedAt()));
            commentList.add(responseDto);
        }


        DetailArticleResponseDto detailArticleResponseDto = new DetailArticleResponseDto(foundArticle);
        detailArticleResponseDto.setTags(responseTags);
        detailArticleResponseDto.setImageFiles(responseImages);
        detailArticleResponseDto.setThumnail(thumnail);
        detailArticleResponseDto.setUsername(findUser.getUsername());
        detailArticleResponseDto.setProfileImage(profileImage);
        detailArticleResponseDto.setCommentList(commentList);
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
            int days = (period.getDays())*-1;
            if(days < 1){
                LocalDateTime nowTime = LocalDateTime.now();
                LocalDateTime createdTime = articleList.get(i).getCreatedAt();
                Duration duration = Duration.between(nowTime, createdTime);
                int time = (int) duration.getSeconds();
                dayBefore += (time/3600)*-1;
                dayBefore += "시간 전";
            }else {
                dayBefore += days;
                dayBefore += "일 전";
            }
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
            String profileImage = null;
            User findUser = userRepository.findByNickname(articleList.get(i).getNickname());
            if(findUser.getImgUrl() != null){
                profileImage = findUser.getImgUrl();
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
        List<Article> foundArticles = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println(currentDateTime);
        if (options.equals("today")) {
            System.out.println(currentDateTime.minusHours(24));
            foundArticles = articleRepository.findByCreatedAtBetweenOrderByLikesDesc(currentDateTime.minusHours(24), LocalDateTime.now());
        }
        if (options.equals("week")) {
            System.out.println(currentDateTime.minusWeeks(1));
            foundArticles = articleRepository.findByCreatedAtBetweenOrderByLikesDesc(currentDateTime.minusWeeks(1), LocalDateTime.now());
        }
        if (options.equals("month")) {
            System.out.println(currentDateTime.minusMonths(1));
            foundArticles = articleRepository.findByCreatedAtBetweenOrderByLikesDesc(currentDateTime.minusMonths(1), LocalDateTime.now());
        }
        List<Article> articleList = foundArticles.stream().distinct().collect(Collectors.toList());
        List<ArticlesResponseDto> articlesResponseDtoList = new ArrayList<>();
        for(int i = 0; i < articleList.size(); i++) {
            // 리스트안에 담아줄 객체 생성
            ArticlesResponseDto responseDto = new ArticlesResponseDto(articleList.get(i));
            // 생성일자 몇일전인지 비교
            LocalDate nowTime = LocalDate.from(LocalDateTime.now());
            LocalDate articleTime = LocalDate.from(articleList.get(i).getCreatedAt());
            Period period = Period.between(nowTime, articleTime);
            String dayBefore = "";
            int days = (period.getDays())*-1;
            if(days < 1){
                LocalDateTime nowTime2 = LocalDateTime.now();
                LocalDateTime createdTime = articleList.get(i).getCreatedAt();
                Duration duration = Duration.between(nowTime2, createdTime);
                int time = (int) duration.getSeconds();
                dayBefore += (time/3600)*-1;
                dayBefore += "시간 전";
            }else {
                dayBefore += days;
                dayBefore += "일 전";
            }
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
            String profileImage = null;
            User findUser = userRepository.findByNickname(articleList.get(i).getNickname());
            if(findUser.getImgUrl() != null){
                profileImage = findUser.getImgUrl();
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

    // 내가 작성한 게시글 목록 조회
    @Transactional
    public List<MyArticlesResponseDto> getMyArticles(String nickname) {

        List<MyArticlesResponseDto> responseDtoList = new ArrayList<>();

        //nickname 가진 유저
        User user = userRepository.findByNickname(nickname);

        if(user == null) {
            throw new IllegalArgumentException("탈퇴한 회원입니다.");
        }

        List<Article> articleList = articleRepository.findAllByNickname(nickname);

        for(Article eachArticle : articleList){
            Long postingId = eachArticle.getId();
            String title = eachArticle.getTitle();
            String content = eachArticle.getContent();

            String thumnail = "";
            if(eachArticle.getImageFiles().size() == 0){
                thumnail = null;
            }else {
                thumnail = eachArticle.getImageFiles().get(0).getImageFile();
            }

            LocalDate currentDateTime = LocalDate.from(LocalDateTime.now());
            LocalDate articleTime = LocalDate.from(eachArticle.getCreatedAt());
            Period period = Period.between(currentDateTime, articleTime);
            String dayBefore = "";
            int days = (period.getDays())*-1;
            if(days < 1){
                LocalDateTime nowTime = LocalDateTime.now();
                LocalDateTime createdTime = eachArticle.getCreatedAt();
                Duration duration = Duration.between(nowTime, createdTime);
                int time = (int) duration.getSeconds();
                dayBefore += (time/3600)*-1;
                dayBefore += "시간 전";
            } else {
                dayBefore += days;
                dayBefore += "일 전";
            }

            List<ArticleTag> articleTags = articleTagRepository.findAllByArticle_Id(postingId);
            List<String> tagNameList = new ArrayList<>();
            for(ArticleTag eachArticleTags : articleTags){
                Long tagId = eachArticleTags.getTag().getId();
                Optional<Tag> tagTemp = tagRepository.findById(tagId);
                String tagName = tagTemp.get().getTagName();
                tagNameList.add(tagName);
            }
            MyArticlesResponseDto responseDto = new MyArticlesResponseDto();
            responseDto.setPostingId(postingId);
            responseDto.setTitle(title);
            responseDto.setContent(content);
            responseDto.setCommentCnt(eachArticle.getComments().size());
            responseDto.setThumnail(thumnail);
            responseDto.setDayBefore(dayBefore);
            responseDto.setTag(tagNameList);

            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }
}
