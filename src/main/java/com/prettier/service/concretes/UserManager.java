package com.prettier.service.concretes;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.mapper.UserMapper;
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

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManager implements UserService {

    private final RoleRepository roleRepository;
    private final RoleService roleService;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
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

        //!!! Toplantiya katilacak ögrenciler icin yeni meeting saatlerinde cakisma var mi kontrolü
        for (Long roleId : userRequest.getRoleIds()) {
            boolean check = roleRepository.existsById(roleId);
            if (!check)
                throw new RoleNotFoundException(language, FriendlyMessageCodes.ROLE_ALREADY_EXISTS, "Role already exists");
        }

        //Set<Role> roles = roleService.getRoleByIds(userRequest.getRoleIds());
        User user = userMapper.toUser(userRequest);


//        Set<Role> roles = new HashSet<>();
//        for (Role roleName : userRequest.getRoles()) {
//            Role role = roleService.getRoleByType(roleName);
//
//            roles.add(role);
//        }
//
//        user.setRoles(roles);
        User userResponse = userRepository.save(user);
        log.debug("[{}][registerUser] -> response: {}", this.getClass().getSimpleName(), userResponse);
        return userMapper.toResponse(userResponse);
    }

    //Runner tarafi icin gerekli method
    @Override
    public long countAllAdmin() {

        return userRepository.count();

    }

//    @Override
//    public Set<Role> getRolesById(Set<Long> roleIds) {
//        return userRepository.findByRole_IdsEquals(roleIds);
//    }

    @Override
    public UserResponse save(Language language, UserRequest userRequest) {

        log.debug("[{}][createRole] -> request: {}", this.getClass().getSimpleName(), userRequest);
//List<Role> roles = userRepository.

        return null;
    }

//    // NOT: RoleService icin yazilan metotlar
//    public List<Role> getUserByIds(Long[] userIds) {
//        //return userRepository.findByIdsEquals(userIds);
//        return userRepository.findByRoleIdsEquals(userIds);
//    }
}
