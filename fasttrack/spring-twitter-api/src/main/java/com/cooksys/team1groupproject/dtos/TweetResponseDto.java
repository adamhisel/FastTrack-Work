package com.cooksys.team1groupproject.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
public class TweetResponseDto {

    private Long id;

    private UserResponseDto author;

    private Timestamp posted;

    private String content;

    private TweetResponseDto inReplyTo;

    private TweetResponseDto repostOf;
}
