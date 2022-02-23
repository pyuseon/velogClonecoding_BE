package com.clonecoding.velogclone_be.controller;


import com.clonecoding.velogclone_be.dto.TagSearchResponseDto;
import com.clonecoding.velogclone_be.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TagController {
    private final TagService tagService;

    //태그 조회
    @GetMapping("/api/posting/tag/{tagName}")
    public ResponseEntity<List<TagSearchResponseDto>> searchTag(@PathVariable String tagName){
        List<TagSearchResponseDto> tagSearchResponseDtoList = tagService.searchTag(tagName);
        return ResponseEntity.ok(tagSearchResponseDtoList);
    }

}
