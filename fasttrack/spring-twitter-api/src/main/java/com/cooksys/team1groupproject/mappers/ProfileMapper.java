package com.cooksys.team1groupproject.mappers;

import com.cooksys.team1groupproject.dtos.HashtagDto;
import com.cooksys.team1groupproject.dtos.ProfileDto;
import com.cooksys.team1groupproject.entities.Hashtag;
import com.cooksys.team1groupproject.entities.Profile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileDto entityToDto(Profile entity);

    List<ProfileDto> entitiesToDtos(List<Profile> entities);

    Profile dtoToEntity(ProfileDto profileDto);
}
