package com.example.bloguserservice.service.impl;

import com.example.bloguserservice.api.v1.DTO.RememberTokenDTO;
import com.example.bloguserservice.api.v1.DTO.UserDTO;
import com.example.bloguserservice.api.v1.mapper.RememberTokenMapper;
import com.example.bloguserservice.api.v1.mapper.UserMapper;
import com.example.bloguserservice.controller.UserController;
import com.example.bloguserservice.exceptions.NotFoundException;
import com.example.bloguserservice.model.RememberToken;
import com.example.bloguserservice.model.User;
import com.example.bloguserservice.repository.RememberTokenRepository;
import com.example.bloguserservice.repository.UserRepository;
import com.example.bloguserservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements  UserService{
    private final UserRepository userDao;
    private final RememberTokenRepository rememberTokenDao;

    private UserMapper userMapper;

    private RememberTokenMapper rememberTokenMapper;

    final String alphabet = "0123456789ABCDE";
    final int N = alphabet.length();

    public UserServiceImpl(UserRepository userDao, RememberTokenRepository rememberTokenDao, UserMapper userMapper, RememberTokenMapper rememberTokenMapper) {
        this.userDao = userDao;
        this.rememberTokenDao = rememberTokenDao;
        this.userMapper = userMapper;
        this.rememberTokenMapper = rememberTokenMapper;
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = (List<User>) this.userDao.findAll();
        return users.stream().map(user -> {
            UserDTO userDTO = userMapper.userToUserDTO(user);
            userDTO.setUserUrl(getUserUrl(user.getId()));
            userDTO.setFullName(returnUserFullName(user));

            return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUser(Long id) {
        Optional<User> user = this.userDao.findById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("User Not Found. for ID value " + id);
        }

        return user.map(userMapper::userToUserDTO)
                .map(userDTO -> {
                    userDTO.setUserUrl(getUserUrl(user.get().getId()));
                    userDTO.setFullName(returnUserFullName(user.get()));
                    return userDTO;
                });
    }

    @Override
    public UserDTO save(User user) throws Exception {
        Optional<User> checkUer = this.userDao.findUserByEmail(user.getEmail());
        if (checkUer.isPresent()) {
            throw new Exception("A user with this email already exist " + checkUer.get().getEmail());
        }
        //save user
        User savedUser = this.userDao.save(user);

        RememberToken rememberToken = new RememberToken();
        rememberToken.setToken(generateRandomToken(20));

        //adding 20 minutes to the current time
        Calendar present = Calendar.getInstance();
        long timeInSecs = present.getTimeInMillis();
        Date expiredAt = new Date(timeInSecs + (20 * 60 * 1000));

        //save token
        rememberToken.setUser(savedUser);
        rememberToken.setExpiredAt(expiredAt);
        RememberToken savedToken = this.rememberTokenDao.save(rememberToken);

        //remember token dto
        RememberTokenDTO rememberTokenDTO = this.rememberTokenMapper.rememberTokenToRememberTokenDTO(savedToken);
        rememberTokenDTO.setUserURL(getUserUrl(savedUser.getId()));

        //add token
        savedUser.setToken(savedToken);

        //update user
        Optional<UserDTO> returnDTO = this.update(savedUser, savedUser.getId());

        if (returnDTO.isPresent()) {
            returnDTO.get().setUserUrl(getUserUrl(savedUser.getId()));
            returnDTO.get().setFullName(returnUserFullName(savedUser));
            returnDTO.get().setToken(rememberTokenDTO);
        }

        return returnDTO.get();
    }

    @Override
    public void delete(long id) {
        this.userDao.deleteById(id);
    }

    @Override
    public Optional<UserDTO> update(User user, long id) {
        Optional<User> currentUser = this.userDao.findById(id);
        if (currentUser.isEmpty()) {
            throw new NotFoundException("User Not Found. for ID value" + id);
        }

        return currentUser.map(user1 -> {
            if (user.getEmail() != null) {
                user1.setEmail(user.getEmail());
            }

            if (user.getSurname() != null) {
                user1.setSurname(user.getSurname());
            }

            if (user.getOtherNames() != null) {
                user1.setOtherNames(user.getOtherNames());
            }

            if (user.getProfile() != null) {
                user1.setProfile(user.getProfile());
            }

            if (user.getIsActive() == 0 || user.getIsActive() == 1) {
                user1.setIsActive(user.getIsActive());
            }

            return saveAndReturnDTO(user1);
        });
    }

    private UserDTO saveAndReturnDTO(User user) {
        User savedUser = this.userDao.save(user);

        UserDTO returnDTO = userMapper.userToUserDTO(savedUser);

        returnDTO.setFullName(returnUserFullName(savedUser));
        returnDTO.setUserUrl(getUserUrl(savedUser.getId()));

        return returnDTO;
    }

    @Override
    public String generateRandomToken(int length) {
        String token = "";

        Random r = new Random();
        for (int i = 0; i < length; i++) {
            String s = token + alphabet.charAt(r.nextInt(N));

            token = s;
        }
        return token;
    }

    private String getUserUrl(long id) {
        return UserController.BASE_URL + "/" + id;
    }

    private String returnUserFullName(User user) {
        return user.getSurname() + " " + user.getOtherNames();
    }
}
