package com.cooksys.team1groupproject.services.impl;

import com.cooksys.team1groupproject.dtos.HashtagDto;
import com.cooksys.team1groupproject.dtos.TweetResponseDto;
import com.cooksys.team1groupproject.entities.Tweet;
import com.cooksys.team1groupproject.exceptions.NotFoundException;
import com.cooksys.team1groupproject.mappers.HashtagMapper;
import com.cooksys.team1groupproject.mappers.TweetMapper;
import com.cooksys.team1groupproject.repositories.HashtagRepository;
import com.cooksys.team1groupproject.repositories.TweetRepository;
import com.cooksys.team1groupproject.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    // GET tags
    @Override
    public List<HashtagDto> getTags() {
        return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
    }

    // GET tags/{label}
    @Override
    public List<TweetResponseDto> getTweetsByHashtag(String label) {
        List<Tweet> tweets = tweetRepository.findAllByHashtagsLabelContainingAndDeletedFalse(label);
        if (tweets.isEmpty()) {
            throw new NotFoundException("No tweets found");
        }
        return tweetMapper.entitiesToDtos(tweets);
    }

}
