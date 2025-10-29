package com.cooksys.team1groupproject.controllers;

import com.cooksys.team1groupproject.dtos.HashtagDto;
import com.cooksys.team1groupproject.dtos.TweetResponseDto;
import com.cooksys.team1groupproject.services.HashtagService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

    private final HashtagService hashtagService;

    @GetMapping
    public List<HashtagDto> getAllHashtags() {
        return hashtagService.getTags();
    }

    @GetMapping("/{label}")
    public List<TweetResponseDto> getTweetsByHashtag(@PathVariable String label) {
        return hashtagService.getTweetsByHashtag(label);
    }

}
