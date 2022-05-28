package com.example.bloguserservice.api.v1.mapper;

import com.example.bloguserservice.api.v1.DTO.RememberTokenDTO;
import com.example.bloguserservice.model.RememberToken;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RememberTokenMapper {
    RememberTokenMapper INSTANCE = Mappers.getMapper(RememberTokenMapper.class);

    RememberTokenDTO rememberTokenToRememberTokenDTO(RememberToken rememberToken);

    RememberToken rememberTokenDTOToRememberToken(RememberTokenDTO rememberTokenDTO);
}
