package com.prettier.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerTest {

    @PostMapping("/api/v1/users")
    void createUser() {
    }
}
