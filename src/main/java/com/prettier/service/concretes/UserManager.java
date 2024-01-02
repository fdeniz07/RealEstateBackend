package com.prettier.service.concretes;

import com.prettier.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class UserManager implements Serializable {
    private final UserRepository userRepository;





}
