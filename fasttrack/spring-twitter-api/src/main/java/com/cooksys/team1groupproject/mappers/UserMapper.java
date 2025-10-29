package com.cooksys.team1groupproject.mappers;

import com.cooksys.team1groupproject.dtos.UserRequestDto;
import com.cooksys.team1groupproject.dtos.UserResponseDto;
import com.cooksys.team1groupproject.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "credentials.username", target = "username")
    UserResponseDto entityToDto(User entity);

    List<UserResponseDto> entitiesToDtos(List<User> entities);

    User dtoToEntity(UserRequestDto userRequestDto);
}
