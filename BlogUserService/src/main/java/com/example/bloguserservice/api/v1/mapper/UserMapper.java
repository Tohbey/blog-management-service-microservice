package com.example.bloguserservice.api.v1.mapper;

import com.example.bloguserservice.api.v1.DTO.UserDTO;
import com.example.bloguserservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
