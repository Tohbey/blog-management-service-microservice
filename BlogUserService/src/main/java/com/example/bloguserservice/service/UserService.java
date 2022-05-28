package com.example.bloguserservice.service;

import com.example.bloguserservice.api.v1.DTO.UserDTO;
import com.example.bloguserservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUser();

    Optional<UserDTO> getUser(Long id);

    UserDTO save(User user) throws Exception;

    void delete(long id);

    Optional<UserDTO> update(User user, long id);

    String generateRandomToken(int length);
}
