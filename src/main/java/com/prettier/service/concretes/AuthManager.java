package com.prettier.service.concretes;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.mapper.AuthMapper;
import com.prettier.payload.request.concretes.LoginRequest;
import com.prettier.payload.request.concretes.SignUpRequest;
import com.prettier.payload.response.concretes.LoginResponse;
import com.prettier.payload.response.concretes.SignUpResponse;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import com.prettier.security.jwt.JWTService;
import com.prettier.service.abstracts.AuthService;
import com.prettier.service.abstracts.RoleService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.auths.SignUpFailedException;
import com.prettier.shared.utils.enums.Language;
import com.prettier.shared.utils.validations.CheckUniqueFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthManager implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final CheckUniqueFields checkUniqueFields;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;


    public SignUpResponse signUp(Language language, SignUpRequest signUpRequest) {

        log.debug("[{}][signUp] -> request: {}", this.getClass().getSimpleName(), signUpRequest);

        // Username, Email ve Phone var mi kontrolü
        if (checkUniqueFields.checkDuplicate(language, signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPhone())) {
            throw new SignUpFailedException(language, FriendlyMessageCodes.SIGN_UP_FAILED_EXCEPTION, "Sign up failed!");
        }
        // User user = authMapper.toUser(signUpRequest);
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPhone(signUpRequest.getPhone());
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));

        Set<Role> defaultRole = roleService.getByRoleName("CUSTOMER");
        user.setRoles(defaultRole);

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        log.debug("[{}][signUp] -> response: {}", this.getClass().getSimpleName(), jwtToken);
//        SignUpResponse response = authMapper.toResponse(newUser);
        return SignUpResponse.builder()
                .token(jwtToken)
                .id(user.getId())
//                .userName(user.getUsername())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .email(user.getEmail())
//                .phone(user.getPhone())
//                .password(user.getPasswordHash())
//                .createAt(user.getCreateAt())
                .build();
    }

    @Override
    public LoginResponse login(Language language, LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(jwtToken)
//                .userName(loginRequest.getUserName())
//                .password(loginRequest.getPassword())
                .build();
    }

}
