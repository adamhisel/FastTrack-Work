package com.cooksys.team1groupproject.services;

import com.cooksys.team1groupproject.dtos.CredentialsDto;
import com.cooksys.team1groupproject.dtos.TweetResponseDto;
import com.cooksys.team1groupproject.dtos.UserRequestDto;
import com.cooksys.team1groupproject.dtos.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserByUsername(String username);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(String username, UserRequestDto userRequestDto);

    UserResponseDto deleteUser(String username, CredentialsDto credentialsDto);

    void followUser(String username, CredentialsDto credentialsDto);

    void unfollowUser(String username, CredentialsDto credentialsDto);

    List<TweetResponseDto> getFeed(String username);

    List<TweetResponseDto> getTweets(String username);

    List<TweetResponseDto> getMentions(String username);

    List<UserResponseDto> getFollowers(String username);

    List<UserResponseDto> getFollowing(String username);
}
