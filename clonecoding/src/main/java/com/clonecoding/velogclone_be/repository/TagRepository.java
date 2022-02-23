package com.clonecoding.velogclone_be.repository;

import com.clonecoding.velogclone_be.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByTagName(String tagName);
}
