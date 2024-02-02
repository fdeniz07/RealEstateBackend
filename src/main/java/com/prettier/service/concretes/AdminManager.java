package com.prettier.service.concretes;

import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.response.concretes.UserResponse;
import com.prettier.repository.UserRepository;
import com.prettier.service.abstracts.AdminService;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminManager implements AdminService {

    private final UserRepository userRepository;

    //Not: getAllUsers() ******************************************************************************************************
    @Override
    public Page<UserResponse> getAllUsers(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: getAllUsersByActive() **********************************************************************************************
    @Override
    public Page<UserResponse> getAllUsersByActive(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: getAllManagers() ***************************************************************************************************
    @Override
    public Page<UserResponse> getAllManagers(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: getAllCustomers() **************************************************************************************************
    @Override
    public Page<UserResponse> getAllCustomers(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: addUser() **********************************************************************************************************
    @Override
    public UserResponse add(Language language, UserRequest userRequest) {
        return null;
    }

    //Not: changeUserRole() ***************************************************************************************************
    @Override
    public UserResponse changeUserRole(Language language, Long id) {
        return null;
    }

    //Not: changeUserStatus() *************************************************************************************************
    @Override
    public UserResponse changeUserStatus(Language language, Long id) {
        return null;
    }
}
