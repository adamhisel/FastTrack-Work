package com.cooksys.team1groupproject.controllers;


import com.cooksys.team1groupproject.dtos.CredentialsDto;
import com.cooksys.team1groupproject.dtos.TweetResponseDto;
import com.cooksys.team1groupproject.dtos.UserRequestDto;
import com.cooksys.team1groupproject.dtos.UserResponseDto;
import com.cooksys.team1groupproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    @GetMapping
    public List<UserResponseDto> getAllUsers(){ return userService.getAllUsers();}

    @GetMapping("/@{username}")
    public UserResponseDto getUserByUsername(@PathVariable String username){ return userService.getUserByUsername(username);}

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto){ return userService.createUser(userRequestDto);}

    @PatchMapping("/@{username}")
    public UserResponseDto updateUser(@PathVariable String username, @RequestBody UserRequestDto userRequestDto){ return userService.updateUser(username, userRequestDto);}

    @DeleteMapping("/@{username}")
    public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto){ return userService.deleteUser(username, credentialsDto);}

    @PostMapping("/@{username}/follow")
    public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto){ userService.followUser(username, credentialsDto);}

    @PostMapping("/@{username}/unfollow")
    public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto){ userService.unfollowUser(username, credentialsDto);}

    @GetMapping("/@{username}/feed")
    public List<TweetResponseDto> getFeed(@PathVariable String username) { return userService.getFeed(username);}

    @GetMapping("/@{username}/tweets")
    public List<TweetResponseDto> getTweets(@PathVariable String username) { return userService.getTweets(username);}

    @GetMapping("/@{username}/mentions")
    public List<TweetResponseDto> getMentions(@PathVariable String username) { return userService.getMentions(username);}

    @GetMapping("/@{username}/followers")
    public List<UserResponseDto> getFollowers(@PathVariable String username) { return userService.getFollowers(username);}

    @GetMapping("/@{username}/following")
    public List<UserResponseDto> getFollowing(@PathVariable String username) { return userService.getFollowing(username);}

}
