package com.cooksys.team1groupproject.mappers;

import com.cooksys.team1groupproject.dtos.CredentialsDto;
import com.cooksys.team1groupproject.dtos.HashtagDto;
import com.cooksys.team1groupproject.entities.Credentials;
import com.cooksys.team1groupproject.entities.Hashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    CredentialsDto entityToDto(Credentials entity);

    List<CredentialsDto> entitiesToDtos(List<Credentials> entities);

    Credentials dtoToEntity(CredentialsDto credentialsDto);
}
