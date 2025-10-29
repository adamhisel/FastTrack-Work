package com.cooksys.team1groupproject.services;

import com.cooksys.team1groupproject.dtos.*;

import java.util.List;

public interface TweetService {

    // GET tweets
    List<TweetResponseDto> getTweets();

    // POST tweets
    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    // GET tweets/{id}
    TweetResponseDto getTweetById(Long id);

    // DELETE tweets/{id}
    TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);

    // POST tweets/{id}/like
    void createLike(Long id, CredentialsDto credentialsDto);

    // POST tweets/{id}/reply
    TweetResponseDto createReply(Long id, TweetRequestDto tweetRequestDto);

    // POST tweets/{id}/repost
    TweetResponseDto createRepost(Long id, CredentialsDto credentialsDto);

    // GET tweets/{id}/tags
    List<HashtagDto> getTags(Long id);

    // GET tweets/{id}/likes
    List<UserResponseDto> getLikes(Long id);

    // GET tweets/{id}/context
    ContextDto getContext(Long id);

    // GET tweets/{id}/replies
    List<TweetResponseDto> getReplies(Long id);

    // GET tweets/{id}/reposts
    List<TweetResponseDto> getReposts(Long id);

    // GET tweets/{id}/mentions
    List<UserResponseDto> getMentions(Long id);

}