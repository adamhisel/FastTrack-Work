package com.cooksys.team1groupproject.services.impl;

import com.cooksys.team1groupproject.entities.Hashtag;
import com.cooksys.team1groupproject.entities.User;
import com.cooksys.team1groupproject.repositories.HashtagRepository;
import com.cooksys.team1groupproject.repositories.UserRepository;
import com.cooksys.team1groupproject.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;

    // GET validate/tag/exists/{label}
    @Override
    public Boolean validateTag(String label) {
        Optional<Hashtag> hashtagOptional = hashtagRepository.findByLabel(label);

        return hashtagOptional.isPresent();
    }

    // GET validate/username/exists/@{username}
    @Override
    public Boolean validateUsername(String username) {
        Optional<User> userOptional = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

        return userOptional.isPresent();
    }

    // GET validate/username/available/@{username}
    @Override
    public Boolean validateUsernameIsAvailable(String username) {
        Optional<User> userOptional = userRepository.findByCredentialsUsername(username);
        return userOptional.isEmpty();
    }
}
