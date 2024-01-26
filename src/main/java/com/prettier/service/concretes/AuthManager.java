package com.prettier.service.concretes;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.mapper.AuthMapper;
import com.prettier.payload.request.concretes.SignUpRequest;
import com.prettier.payload.response.concretes.SignUpResponse;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import com.prettier.service.abstracts.AuthService;
import com.prettier.service.abstracts.RoleService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.auths.SignUpFailedException;
import com.prettier.shared.utils.enums.Language;
import com.prettier.shared.utils.validations.CheckUniqueFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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


    public SignUpResponse signUp(Language language, SignUpRequest signUpRequest) {

        log.debug("[{}][signUp] -> request: {}", this.getClass().getSimpleName(), signUpRequest);


        // Username, Email ve Phone var mi kontrolü
        if (checkUniqueFields.checkDuplicate(language, signUpRequest.getUserName(), signUpRequest.getEmail(), signUpRequest.getPhone())) {
            throw new SignUpFailedException(language, FriendlyMessageCodes.SIGN_UP_FAILED_EXCEPTION, "Sign up failed!");
        }
        User user = authMapper.toUser(signUpRequest);
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));

        Set<Role> role = roleService.getByRoleName("CUSTOMER"); //!!!NOT : RoleRepository Optional yapilacak ve burayada IFriendlyMessage eklenecek .orElseThrow(()-> new RoleNotFoundException(language,"Role not found"));
        user.setRoles(role);

        User newUser = userRepository.save(user);

        log.debug("[{}][signUp] -> response: {}", this.getClass().getSimpleName(), newUser);
        SignUpResponse response = authMapper.toResponse(newUser);

//        SignUpResponse response = new SignUpResponse();
//        response.setFirstName(newUser.getFirstName());
//        response.setLastName(newUser.getLastName());
//        response.setUserName(newUser.getUserName());
//        response.setEmail(newUser.getEmail());
//        response.setPhone(newUser.getPhone());
//
//
//        Set<Role> roles = new HashSet<>();
//        roles.addAll(roleService.getByUser(user));
//
//        response.setRolesIds(roles);
        return response;


    }

}
