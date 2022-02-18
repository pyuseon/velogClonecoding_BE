package com.clonecoding.velogclone_be.repository;

import com.clonecoding.velogclone_be.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
