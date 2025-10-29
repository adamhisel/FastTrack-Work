package com.cooksys.team1groupproject.controllers;

import com.cooksys.team1groupproject.dtos.*;
import com.cooksys.team1groupproject.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getTweets() {
        return tweetService.getTweets();
    }

    @PostMapping
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id) {
        return tweetService.getTweetById(id);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.deleteTweet(id, credentialsDto);
    }

    @PostMapping("{id}/like")
    public void createLike(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        tweetService.createLike(id, credentialsDto);
    }

    @PostMapping("{id}/reply")
    public TweetResponseDto createReply(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createReply(id, tweetRequestDto);
    }

    @PostMapping("{id}/repost")
    public TweetResponseDto createRepost(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.createRepost(id, credentialsDto);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTags(@PathVariable Long id) {
        return tweetService.getTags(id);
    }

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getLikes(@PathVariable Long id) {
        return tweetService.getLikes(id);
    }

    @GetMapping("/{id}/context")
    public ContextDto getContext(@PathVariable Long id) {
        return tweetService.getContext(id);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getReplies(@PathVariable Long id) {
        return tweetService.getReplies(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getReposts(@PathVariable Long id) {
        return tweetService.getReposts(id);
    }

    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getMentions(@PathVariable Long id) {
        return tweetService.getMentions(id);
    }
}
