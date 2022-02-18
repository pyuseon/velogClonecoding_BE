package com.clonecoding.velogclone_be.repository;

import com.clonecoding.velogclone_be.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
