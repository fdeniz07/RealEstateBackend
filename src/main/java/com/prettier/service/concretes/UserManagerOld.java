package com.prettier.service.concretes;

import com.prettier.payloads.mapper.UserRoleMapper;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import com.prettier.service.abstracts.RoleService;
import com.prettier.shared.utils.validations.CheckUniqueFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManagerOld {

    private final RoleRepository roleRepository;
    private final RoleService roleService;

    private final UserRepository userRepository;
    private final UserRoleMapper userRoleMapper;
    private final CheckUniqueFields checkUniqueFields;

   // PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Kullanicinin girdigi Passwordu Clear Text'dem hash lenmis hale dönüstürmeye yarar

    //Not: registerUser() ******************************************************************************************************
//    @Override
//    public UserResponse register(Language language, UserRequest userRequest) {
//
//        log.debug("[{}][registerUser] -> request: {}", this.getClass().getSimpleName(), userRequest);
//
//        //!!! Dublicate Kontrolü
//        checkUniqueFields.checkDuplicate(language,
//                userRequest.getUserName(),
//                userRequest.getEmail(),
//                userRequest.getPhone()
//        );
//
//        //!!! Ilgili rol DB de var mi kontrolü
//
//        User user = userRoleMapper.toUser(userRequest);
//        //user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash())); // Password u hash liyoruz
//
//        Set<Role> roles = new HashSet<>();
//        for (Long roleId : userRequest.getRoleIds()) {
//            Role role = roleRepository.findById(roleId)
//                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
//            roles.add(role);
//        }
//
//        user.setRoles(roles);
//
//        User userResponse = userRepository.save(user);
//        log.debug("[{}][registerUser] -> response: {}", this.getClass().getSimpleName(), userResponse);
//        return userRoleMapper.toResponse(userResponse);
//    }
//
//    //Runner tarafi icin gerekli method
//    @Override
//    public long countAllAdmin() {
//
//        return userRepository.count();
//
//    }
//
//    @Override
//    public UserResponse login(Language language, UserRequest userRequest) {
//        return null;
//    }
//
//    @Override
//    public Page<UserResponse> getUsers(Language language, int page, int size, String sort, String type) {
//
//        log.debug("[{}][getUsers]", this.getClass().getSimpleName());
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
//        if (Objects.equals(type, "desc")) {
//            PageRequest.of(page, size, Sort.by(sort).descending());
//        }
//
//        List<UserResponse> users = userRepository.findAll()
//                .stream()
//                .map(userRoleMapper::toResponse)
//                .collect(Collectors.toList());
//
//        if (users.isEmpty()) {
//            throw new CategoryNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND_EXCEPTION, "Users not found");
//        }
//        log.debug("[{}][getUsers] -> response: {}", this.getClass().getSimpleName(), users);
//        return userRepository.findAll(pageable).map(userRoleMapper::toResponse);
//    }

}
