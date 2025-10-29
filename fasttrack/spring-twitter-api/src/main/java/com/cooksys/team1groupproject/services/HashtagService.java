package com.cooksys.team1groupproject.services;

import com.cooksys.team1groupproject.dtos.HashtagDto;
import com.cooksys.team1groupproject.dtos.TweetResponseDto;

import java.util.List;

public interface HashtagService {

    // GET tags
    List<HashtagDto> getTags();

    // GET tags/{label}
    List<TweetResponseDto> getTweetsByHashtag(String label);

}
