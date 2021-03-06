package com.clonecoding.velogclone_be.service;

import com.clonecoding.velogclone_be.dto.comment.CommentRequestDto;
import com.clonecoding.velogclone_be.dto.comment.CommentResponseDto;
import com.clonecoding.velogclone_be.dto.comment.CreatCommentResponseDto;
import com.clonecoding.velogclone_be.model.Article;
import com.clonecoding.velogclone_be.model.Comment;
import com.clonecoding.velogclone_be.repository.ArticleRepository;
import com.clonecoding.velogclone_be.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;


    //댓글 작성
    @Transactional
    public CreatCommentResponseDto creatComment(CommentRequestDto commentRequestDto){
        Article article = articleRepository.findById(commentRequestDto.getPostingId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );
        Comment comment = new Comment(commentRequestDto.getNickname(), commentRequestDto.getComment(), article);
        commentRepository.save(comment);

        CreatCommentResponseDto creatCommentResponseDto = new CreatCommentResponseDto();
        creatCommentResponseDto.setCommentId(comment.getCommentId());
        creatCommentResponseDto.setCreatedAtComment(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(comment.getCreatedAt()));
        creatCommentResponseDto.setMsg("댓글 작성이 완료되었습니다.");

        return creatCommentResponseDto;
    }

    //댓글 수정
    public Comment updataComment(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        comment.updateComment(commentRequestDto.getComment());
        return commentRepository.save(comment);
    }


}
