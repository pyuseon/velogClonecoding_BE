package com.clonecoding.velogclone_be.service;

import com.clonecoding.velogclone_be.repository.ArticleRepository;
import com.clonecoding.velogclone_be.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;


}
