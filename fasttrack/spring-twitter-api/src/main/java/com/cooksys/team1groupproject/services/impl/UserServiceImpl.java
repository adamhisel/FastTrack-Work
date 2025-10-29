package com.cooksys.team1groupproject.services.impl;

import com.cooksys.team1groupproject.dtos.*;
import com.cooksys.team1groupproject.entities.Credentials;
import com.cooksys.team1groupproject.entities.Profile;
import com.cooksys.team1groupproject.entities.Tweet;
import com.cooksys.team1groupproject.entities.User;
import com.cooksys.team1groupproject.exceptions.BadRequestException;
import com.cooksys.team1groupproject.exceptions.ConflictException;
import com.cooksys.team1groupproject.exceptions.NotAuthorizedException;
import com.cooksys.team1groupproject.exceptions.NotFoundException;
import com.cooksys.team1groupproject.mappers.CredentialsMapper;
import com.cooksys.team1groupproject.mappers.ProfileMapper;
import com.cooksys.team1groupproject.mappers.TweetMapper;
import com.cooksys.team1groupproject.mappers.UserMapper;
import com.cooksys.team1groupproject.repositories.TweetRepository;
import com.cooksys.team1groupproject.repositories.UserRepository;
import com.cooksys.team1groupproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TweetRepository tweetRepository;

    private final UserMapper userMapper;

    private final ProfileMapper profileMapper;

    private final CredentialsMapper credentialsMapper;

    private final TweetMapper tweetMapper;

    private void validateCredentials(Credentials credentials){
        if(credentials == null || credentials.getPassword() == null || credentials.getUsername()== null){
            throw new BadRequestException("Credentials are required");
        }
    }

    private void validateProfile(ProfileDto profileDto){
        Profile profile = profileMapper.dtoToEntity(profileDto);
        if(profile == null || profile.getEmail() == null){
            throw new BadRequestException("Profile email is required");
        }
    }

    private User findExistingUser(String username){
        Optional<User> existingUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(existingUser.isEmpty()){
            throw new NotFoundException("No user found with that username");
        }
        return existingUser.get();
    }
    
    private User findExistingUser(String username, Credentials credentials){
        validateCredentials(credentials);
        Optional<User> existingUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(existingUser.isEmpty()){
            throw new NotFoundException("No user found with that username");
        }
        User user = existingUser.get();
        if(!user.getCredentials().getUsername().equals(credentials.getUsername()) || !user.getCredentials().getPassword().equals(credentials.getPassword())){
            throw new NotAuthorizedException("Username or password is incorrect");
        }

        return user;
    }
    

    private void deleteTweets(User user){
        List<Tweet> tweets = user.getTweets();
        for(Tweet t : tweets){
            t.setDeleted(true);
            tweetRepository.saveAndFlush(t);
        }
    }

    private void restoreTweets(User user){
        List<Tweet> tweets = user.getTweets();
        for(Tweet t : tweets){
            t.setDeleted(false);
            tweetRepository.saveAndFlush(t);
        }
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        if(username.isEmpty()){
            throw new BadRequestException("No username provided");
        }
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if(user.isEmpty()){
            throw new NotFoundException("No user found with that username");
        }
        return userMapper.entityToDto(user.get());
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        validateCredentials(credentialsMapper.dtoToEntity(userRequestDto.getCredentials()));
        validateProfile(userRequestDto.getProfile());
        Optional<User> existingUser = userRepository.findByCredentialsUsernameAndDeletedFalse(userRequestDto.getCredentials().getUsername());
        Optional<User> existingDeletedUser = userRepository.findByCredentialsUsernameAndCredentialsPasswordAndDeletedTrue(userRequestDto.getCredentials().getUsername(), userRequestDto.getCredentials().getPassword());
        User userToCreate;
        if(existingUser.isPresent()){
            throw new ConflictException("Username is already taken");
        }
        else if(existingDeletedUser.isPresent()){
            userToCreate = existingDeletedUser.get();
            userToCreate.setDeleted(false);
            restoreTweets(userToCreate);
        }
        else{
            userToCreate = userMapper.dtoToEntity(userRequestDto);
        }
        return userMapper.entityToDto(userRepository.saveAndFlush(userToCreate));
    }

    @Override
    public UserResponseDto updateUser(String username, UserRequestDto userRequestDto) {
        User user = findExistingUser(username, credentialsMapper.dtoToEntity(userRequestDto.getCredentials()));
        if (userRequestDto.getProfile() != null) {
            ProfileDto p = userRequestDto.getProfile();
            Profile e = user.getProfile();
            if (p.getFirstName() != null && !p.getFirstName().isBlank()) e.setFirstName(p.getFirstName());
            if (p.getLastName()  != null && !p.getLastName().isBlank())  e.setLastName(p.getLastName());
            if (p.getEmail()     != null && !p.getEmail().isBlank())     e.setEmail(p.getEmail());
            if (p.getPhone()     != null && !p.getPhone().isBlank())     e.setPhone(p.getPhone());
        }
        else{
            throw new BadRequestException("Profile is required");
        }
        return userMapper.entityToDto(userRepository.saveAndFlush(user));
    }

    @Override
    public UserResponseDto deleteUser(String username, CredentialsDto credentialsDto) {
        User userToDelete = findExistingUser(username, credentialsMapper.dtoToEntity(credentialsDto));
        userToDelete.setDeleted(true);
        deleteTweets(userToDelete);
        return userMapper.entityToDto(userRepository.saveAndFlush(userToDelete));
    }

    @Override
    public void followUser(String username, CredentialsDto credentialsDto) {
        User userToFollow = findExistingUser(username);
        User followingUser = findExistingUser(credentialsDto.getUsername(), credentialsMapper.dtoToEntity(credentialsDto));
        
        if(followingUser.getFollowedUsers().contains(userToFollow)){
            throw new ConflictException("User already follows " + userToFollow);
        }
        
        followingUser.getFollowedUsers().add(userToFollow);
        userToFollow.getFollowedBy().add(followingUser);
        userRepository.saveAndFlush(userToFollow);
        userRepository.saveAndFlush(followingUser);
    }

    @Override
    public void unfollowUser(String username, CredentialsDto credentialsDto) {
        User userToUnfollow = findExistingUser(username);
        User unfollowingUser = findExistingUser(credentialsDto.getUsername(), credentialsMapper.dtoToEntity(credentialsDto));

        if(!unfollowingUser.getFollowedUsers().contains(userToUnfollow)){
            throw new ConflictException("User already does not follow " + userToUnfollow);
        }

        unfollowingUser.getFollowedUsers().remove(userToUnfollow);
        userToUnfollow.getFollowedBy().remove(unfollowingUser);
        userRepository.saveAndFlush(userToUnfollow);
        userRepository.saveAndFlush(unfollowingUser);
    }

    @Override
    public List<TweetResponseDto> getFeed(String username) {
        User user = findExistingUser(username);
        List<Tweet> feed = tweetRepository.findByAuthorCredentialsUsernameAndDeletedFalse(username);
        for(User u : user.getFollowedUsers()){
            feed.addAll(tweetRepository.findByAuthorCredentialsUsernameAndDeletedFalse(u.getCredentials().getUsername()));
        }
        feed.sort(Comparator.comparing(Tweet::getPosted).reversed());
        return tweetMapper.entitiesToDtos(feed);
    }

    @Override
    public List<TweetResponseDto> getTweets(String username) {
        User user = findExistingUser(username);
        List<Tweet> tweets = tweetRepository.findByAuthorCredentialsUsernameAndDeletedFalse(username);
        tweets.sort(Comparator.comparing(Tweet::getPosted).reversed());
        return tweetMapper.entitiesToDtos(tweets);
    }

    @Override
    public List<TweetResponseDto> getMentions(String username) {
        User user =  findExistingUser(username);
        return tweetMapper.entitiesToDtos(user.getMentions().stream().toList());
    }

    @Override
    public List<UserResponseDto> getFollowers(String username) {
        User user = findExistingUser(username);
        return userMapper.entitiesToDtos(userRepository.findByFollowedUsers_Credentials_UsernameAndDeletedFalse(user.getCredentials().getUsername()));
    }

    @Override
    public List<UserResponseDto> getFollowing(String username) {
        User user = findExistingUser(username);
        return userMapper.entitiesToDtos(userRepository.findByFollowedBy_Credentials_UsernameAndDeletedFalse(user.getCredentials().getUsername()));
    }
}
