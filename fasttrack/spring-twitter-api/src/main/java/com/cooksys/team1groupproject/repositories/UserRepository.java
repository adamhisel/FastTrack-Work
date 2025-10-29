package com.cooksys.team1groupproject.repositories;

import com.cooksys.team1groupproject.entities.Tweet;
import com.cooksys.team1groupproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByDeletedFalse();
    Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);
    Optional<User> findByCredentialsUsernameAndCredentialsPasswordAndDeletedTrue(String username, String password);
    Optional<User> findByCredentialsUsername(String username);
    List<User> findByFollowedUsers_Credentials_UsernameAndDeletedFalse(String username);
    List<User> findByFollowedBy_Credentials_UsernameAndDeletedFalse(String username);
    //Set<Tweet> findByMentionsCredentialsUsernameAndDeletedFalse(String credentialsUsername);
}
