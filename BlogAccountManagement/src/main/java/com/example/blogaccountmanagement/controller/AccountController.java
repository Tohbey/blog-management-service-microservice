package com.example.blogaccountmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @Autowired
    private Environment env;

    @GetMapping("/status/check")
    public String status() {
        return "Working on port " + env.getProperty("local-server.port`");
    }
}
