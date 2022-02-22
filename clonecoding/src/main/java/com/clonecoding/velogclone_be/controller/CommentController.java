package com.clonecoding.velogclone_be.controller;

import com.clonecoding.velogclone_be.dto.CommentRequestDto;
import com.clonecoding.velogclone_be.dto.CommentResponseDto;
import com.clonecoding.velogclone_be.repository.CommentRepository;
import com.clonecoding.velogclone_be.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;


    //댓글 작성
    @PostMapping("/api/comment")
    public CommentResponseDto creatComment(@RequestBody CommentRequestDto commentRequestDto){
        return commentService.creatComment(commentRequestDto);

    }

    //댓글 수정
    @PutMapping("/api/comment/{commentId}")
    public String updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        commentService.updataComment(commentId, commentRequestDto);
        return "댓글 수정이 완료되었습니다.";
    }

    //댓글 삭제
    @DeleteMapping("/api/comment/{commentId}")
    public String deleteComment(@PathVariable Long commentId){
        commentRepository.deleteById(commentId);
        return "댓글 삭제가 완료되었습니다.";
    }

}
