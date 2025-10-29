package com.cooksys.team1groupproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Credentials credentials;

    @OneToMany(mappedBy = "author")
    @SQLRestriction("deleted = false")
    private List<Tweet> tweets = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp joined;

    @Column(nullable = false)
    private boolean deleted = false;

    @Embedded
    private Profile profile;

    @ManyToMany
    @JoinTable(
            name = "user_mentions",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Tweet> mentions = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tweet_id")
    )
    private Set<Tweet> likedTweets = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "followers_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> followedUsers = new HashSet<>();

    @ManyToMany(mappedBy = "followedUsers")
    private Set<User> followedBy = new HashSet<>();
}
