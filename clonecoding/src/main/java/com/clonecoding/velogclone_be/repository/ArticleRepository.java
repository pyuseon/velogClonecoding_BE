package com.clonecoding.velogclone_be.repository;

import com.clonecoding.velogclone_be.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByOrderByCreatedAtDesc();
    List<Article> findByCreatedAtBetweenOrderByLikesDesc(LocalDateTime past, LocalDateTime now);
    List<Article> findByCreatedAtBetween(LocalDateTime past, LocalDateTime now);
}
