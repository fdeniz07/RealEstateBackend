package com.prettier.service.concretes;

import com.prettier.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleManager {
    private final RoleRepository roleRepository;

}
