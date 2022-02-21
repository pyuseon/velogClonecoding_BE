package com.clonecoding.velogclone_be.repository;

import com.clonecoding.velogclone_be.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
