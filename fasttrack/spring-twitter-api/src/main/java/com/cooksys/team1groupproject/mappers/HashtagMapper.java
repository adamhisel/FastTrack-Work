package com.cooksys.team1groupproject.mappers;

import com.cooksys.team1groupproject.dtos.HashtagDto;
import com.cooksys.team1groupproject.entities.Hashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    HashtagDto entityToDto(Hashtag entity);

    List<HashtagDto> entitiesToDtos(List<Hashtag> entities);

    Hashtag dtoToEntity(HashtagDto hashtagDto);
}
