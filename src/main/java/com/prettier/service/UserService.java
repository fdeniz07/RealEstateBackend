package com.prettier.service;

import com.prettier.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class UserService implements Serializable {
    private final UserRepository userRepository;





}
