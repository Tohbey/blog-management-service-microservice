package com.example.bloguserservice.controller;

import com.example.bloguserservice.api.v1.DTO.UserDTO;
import com.example.bloguserservice.api.v1.DTO.UserListDTO;
import com.example.bloguserservice.dtos.ResponseObject;
import com.example.bloguserservice.model.User;
import com.example.bloguserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {

    public static final String BASE_URL = "/api/v1/users";

    @Autowired
    private Environment env;

    private final UserService userService;
    private PasswordEncoder passwordEncoder;


    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/status/check")
    public String status() {
        return "Working on port " + env.getProperty("local-server.port`");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getAllUsers() {
        ResponseObject object = new ResponseObject();
        try {
            List<UserDTO> userList = userService.getAllUser();
            object.setData(new UserListDTO(userList));
            object.setValid(true);
            object.setMessage("Resource Retrieved Successfully");
        } catch (Exception e) {
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseObject> getUserById(@PathVariable long id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Optional<UserDTO> userDTO = userService.getUser(id);
            responseObject.setData(userDTO);
            responseObject.setValid(true);
            responseObject.setMessage("Resource Retrieved Successfully");
        } catch (Exception e) {
            responseObject.setValid(false);
            responseObject.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseObject);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResponseObject> insertUser(@RequestBody User user) {
        ResponseObject responseObject = new ResponseObject();
        try {
            String pwd = user.getPassword();
            String encryptPwd = this.passwordEncoder.encode(pwd);
            user.setPassword(encryptPwd);

            UserDTO userDTO = userService.save(user);
            responseObject.setData(userDTO);
            responseObject.setValid(true);
            responseObject.setMessage("Resource Created Successfully");
        } catch (Exception e) {
            responseObject.setValid(false);
            responseObject.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable long id) {
        ResponseObject responseObject = new ResponseObject();
        try {
            userService.delete(id);
            responseObject.setValid(true);
            responseObject.setMessage("Resource Deleted Successfully");
        } catch (Exception e) {
            responseObject.setValid(false);
            responseObject.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseObject);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ResponseObject> updateUser(@PathVariable long id, @RequestBody User user) {
        ResponseObject responseObject = new ResponseObject();
        try {
            Optional<UserDTO> userDTO = userService.update(user, id);
            responseObject.setData(userDTO);
            responseObject.setValid(true);
            responseObject.setMessage("Resource Updated Successfully");
        } catch (Exception e) {
            responseObject.setValid(false);
            responseObject.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(responseObject);
    }
}
