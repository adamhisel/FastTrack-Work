package com.cooksys.team1groupproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author", nullable = false)
    private User author;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp posted;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "inReplyTo")
    private Tweet inReplyTo;

    @OneToMany(mappedBy = "inReplyTo", fetch = FetchType.LAZY)
    private List<Tweet> replies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "repostOf")
    private Tweet repostOf;

    @OneToMany(mappedBy = "repostOf", fetch = FetchType.LAZY)
    private List<Tweet> reposts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "tweet_hashtags",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Set<Hashtag> hashtags = new HashSet<>();

    @ManyToMany(mappedBy = "mentions")
    private Set<User> mentionedBy = new HashSet<>();

    @ManyToMany(mappedBy = "likedTweets")
    private Set<User> likedBy = new HashSet<>();
}
