package com.clonecoding.velogclone_be.repository;

import com.clonecoding.velogclone_be.model.ArticleTag;
import com.clonecoding.velogclone_be.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    List<ArticleTag> findAllByArticle_Id(Long postingId);

    List<ArticleTag> findAllByTag(Tag searchTagName);
}
