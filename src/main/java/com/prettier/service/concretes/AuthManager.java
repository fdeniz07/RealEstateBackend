package com.prettier.service.concretes;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.mapper.AuthMapper;
import com.prettier.payload.request.concretes.SignUpRequest;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import com.prettier.service.abstracts.AuthService;
import com.prettier.service.abstracts.RoleService;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthManager implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final RoleService roleService;

    private final AuthMapper authMapper;

    public User signUp(Language language, SignUpRequest signUpRequest) {

        log.debug("[{}][signUp] -> request: {}", this.getClass().getSimpleName(), signUpRequest);

        User user = new User();

        user.setUserName(signUpRequest.getUserName());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPhone(signUpRequest.getPhone());
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));

        Set<Role> role = roleService.findByRoleName("MANAGER"); //!!!NOT : RoleRepository Optional yapilacak ve burayada IFriendlyMessage eklenecek .orElseThrow(()-> new RoleNotFoundException(language,"Role not found"));
        user.setRoles(role);

       User response = userRepository.save(user);

        log.debug("[{}][signUp] -> response: {}", this.getClass().getSimpleName(), response);
       return response;

    }

}
