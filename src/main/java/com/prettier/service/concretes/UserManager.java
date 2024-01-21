package com.prettier.service.concretes;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.mapper.UserRoleMapper;
import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.response.concretes.UserResponse;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import com.prettier.service.abstracts.RoleService;
import com.prettier.service.abstracts.UserService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.roles.RoleNotFoundException;
import com.prettier.shared.utils.enums.Language;
import com.prettier.shared.utils.validations.CheckUniqueFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManager implements UserService {

    private final RoleRepository roleRepository;
    private final RoleService roleService;

    private final UserRepository userRepository;
    private final UserRoleMapper userRoleMapper;
    private final CheckUniqueFields checkUniqueFields;


    //Not: registerUser() ******************************************************************************************************
    @Override
    public UserResponse register(Language language, UserRequest userRequest) {

        log.debug("[{}][registerUser] -> request: {}", this.getClass().getSimpleName(), userRequest);

        //!!! Dublicate Kontrolü
        checkUniqueFields.checkDuplicate(language,
                userRequest.getUserName(),
                userRequest.getEmail(),
                userRequest.getPhone()
        );

        //!!! Ilgili rol DB de var mi kontrolü

        User user = userRoleMapper.toUser(userRequest);
        Set<Role> roles = new HashSet<>();
        for (Long roleId : userRequest.getRoleIds()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
            roles.add(role);
        }

        user.setRoles(roles);

        User userResponse = userRepository.save(user);
        log.debug("[{}][registerUser] -> response: {}", this.getClass().getSimpleName(), userResponse);
        return userRoleMapper.toResponse(userResponse);
    }

    //Runner tarafi icin gerekli method
    @Override
    public long countAllAdmin() {

        return userRepository.count();

    }

}
