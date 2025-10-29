package com.cooksys.team1groupproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String label;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp firstUsed;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp lastUsed;

    @ManyToMany(mappedBy = "hashtags")
    private Set<Tweet> taggedBy = new HashSet<>();
}
