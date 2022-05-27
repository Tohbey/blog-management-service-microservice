package com.example.bloguserservice.service;

import com.example.bloguserservice.api.v1.DTO.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDetails);
    UserDTO getUserDetailsByEmail(String email);
    UserDTO getUserByUserId(String userId);
}
