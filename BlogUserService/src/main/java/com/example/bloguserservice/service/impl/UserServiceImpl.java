package com.example.bloguserservice.service.impl;

import com.example.bloguserservice.api.v1.DTO.UserDTO;
import com.example.bloguserservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements  UserService{
    @Override
    public UserDTO createUser(UserDTO userDetails) {
        return null;
    }

    @Override
    public UserDTO getUserDetailsByEmail(String email) {
        return null;
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        return null;
    }
}
