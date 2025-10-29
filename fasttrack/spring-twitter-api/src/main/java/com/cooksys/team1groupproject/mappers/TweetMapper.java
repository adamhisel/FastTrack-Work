package com.cooksys.team1groupproject.mappers;

import com.cooksys.team1groupproject.dtos.TweetRequestDto;
import com.cooksys.team1groupproject.dtos.TweetResponseDto;
import com.cooksys.team1groupproject.entities.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {

    TweetResponseDto entityToDto(Tweet entity);

    List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

    Tweet dtoToEntity(TweetRequestDto tweetRequestDto);
}
