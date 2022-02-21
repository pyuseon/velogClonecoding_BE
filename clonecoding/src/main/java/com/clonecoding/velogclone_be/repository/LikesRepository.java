package com.clonecoding.velogclone_be.repository;

import com.clonecoding.velogclone_be.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findOneByUserIdAndArticleId(Long userId, Long ariticleId);
}