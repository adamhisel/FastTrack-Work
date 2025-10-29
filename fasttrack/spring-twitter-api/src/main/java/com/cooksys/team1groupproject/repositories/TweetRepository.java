package com.cooksys.team1groupproject.repositories;

import com.cooksys.team1groupproject.entities.Hashtag;
import com.cooksys.team1groupproject.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Optional<Tweet> findByIdAndDeletedFalse(Long id);

    List<Tweet> findAllByDeletedFalse();
    List<Tweet> findByAuthorCredentialsUsernameAndDeletedFalse(String credentialsUsername);
    List<Tweet> findAllByHashtagsLabelContainingAndDeletedFalse(String label);
}
