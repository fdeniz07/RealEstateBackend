package com.prettier.service.concretes;

import com.prettier.entity.concretes.City;
import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.mapper.AuthMapper;
import com.prettier.payload.mapper.UserMapper;
import com.prettier.payload.mapper.UserMapperForAdmins;
import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.request.concretes.UserRequestForAdmin;
import com.prettier.payload.response.concretes.UserResponseForAdmins;
import com.prettier.repository.UserRepository;
import com.prettier.service.abstracts.AdminService;
import com.prettier.service.abstracts.RoleService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.cities.CityNotCreatedException;
import com.prettier.shared.exception.exceptions.users.UserNotFoundException;
import com.prettier.shared.utils.enums.Language;
import com.prettier.shared.utils.validations.CheckUniqueFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminManager implements AdminService {
    private final AuthMapper authMapper;
    private final UserMapper userMapper;

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapperForAdmins userMapperForAdmins;
    private final CheckUniqueFields checkUniqueFields;

    //Not: getAllUsers() ******************************************************************************************************
    @Override
    public Page<UserResponseForAdmins> getAllUsers(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getAllUsers]", this.getClass().getSimpleName());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        List<UserResponseForAdmins> responses = userRepository.findAll()
                .stream()
                .map(userMapperForAdmins::toResponse)
                .toList();

        if (responses.isEmpty()) {
            throw new UserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND_EXCEPTION, "Users not found");
        }
        log.debug("[{}][getAllUsers] -> response: {}", this.getClass().getSimpleName(), responses);
        return userRepository.findAll(pageable).map(userMapperForAdmins::toResponse);
    }

    //Not: getAllUsersByActive() **********************************************************************************************
    @Override
    public Page<UserResponseForAdmins> getAllUsersByActive(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getAllUsersByActive]", this.getClass().getSimpleName());

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        Page<User> activeUsers = userRepository.findByActiveTrue(pageable);

        if (activeUsers.isEmpty()) {
            throw new UserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND_EXCEPTION, "Users not found");
        }
        log.debug("[{}][getAllUsersByActive] -> response: {}", this.getClass().getSimpleName(), activeUsers);

        return activeUsers.map(userMapperForAdmins::toResponse);
    }


    //Not: getAllUsersByInactive() **********************************************************************************************
    @Override
    public Page<UserResponseForAdmins> getAllUsersByInactive(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getAllUsersByInactive]", this.getClass().getSimpleName());

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        Page<User> inactiveUsers = userRepository.findByActiveFalse(pageable);

        if (inactiveUsers.isEmpty()) {
            throw new UserNotFoundException(language, FriendlyMessageCodes.USER_NOT_FOUND_EXCEPTION, "Users not found");
        }
        log.debug("[{}][getAllUsersByInactive] -> response: {}", this.getClass().getSimpleName(), inactiveUsers);

        return inactiveUsers.map(userMapperForAdmins::toResponse);
    }

    //Not: getAllManagers() ***************************************************************************************************
    @Override
    public Page<UserResponseForAdmins> getAllManagers(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getAllManagers]", this.getClass().getSimpleName());

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        Page<User> managers = userRepository.getUsersByRoleName("MANAGER", pageable);

        if (managers.isEmpty()) {
            throw new UserNotFoundException(language, FriendlyMessageCodes.MANAGER_NOT_FOUND_EXCEPTION, "Managers not found");
        }
        log.debug("[{}][getAllManagers] -> response: {}", this.getClass().getSimpleName(), managers);

        return managers.map(userMapperForAdmins::toResponse);
    }

    //Not: getAllCustomers() **************************************************************************************************
    @Override
    public Page<UserResponseForAdmins> getAllCustomers(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getAllCustomers]", this.getClass().getSimpleName());

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        Page<User> customers = userRepository.getUsersByRoleName("CUSTOMER", pageable);

        if (customers.isEmpty()) {
            throw new UserNotFoundException(language, FriendlyMessageCodes.CUSTOMER_NOT_FOUND_EXCEPTION, "Customers not found");
        }
        log.debug("[{}][getAllCustomers] -> response: {}", this.getClass().getSimpleName(), customers);

        return customers.map(userMapperForAdmins::toResponse);
    }

    //Not: addUser() **********************************************************************************************************
    @Override
    public UserResponseForAdmins add(Language language, UserRequestForAdmin userRequest) {

        log.debug("[{}][addUser] -> request: {}", this.getClass().getSimpleName(), userRequest);

        // Kullanici email, telefon veritabaninda mevcut mu kontrolü
        checkUniqueFields.checkDuplicate(language, userRequest.getEmail(), userRequest.getPhone());

        User user = userMapperForAdmins.toUser(userRequest);

        //TODO : Requestteki roller kullaniciya atanacak
//
//            user.setRoles(userRequest.getRoles());
//
//            roleService.add(language, userRequest.getRoles())
        UserResponseForAdmins response = userMapperForAdmins.toResponse(user);
        log.debug("[{}][addUser] -> response: {}", this.getClass().getSimpleName(), response);

        return response;
    }


    //Not: changeUserRole() ***************************************************************************************************
    @Override
    public UserResponseForAdmins changeUserRole(Language language, Long id) {
        return null;
    }

    //Not: changeUserStatus() *************************************************************************************************
    @Override
    public UserResponseForAdmins changeUserStatus(Language language, Long id) {
        return null;
    }
}
