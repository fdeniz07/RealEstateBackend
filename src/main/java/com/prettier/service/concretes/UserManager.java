package com.prettier.service.concretes;

import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.request.concretes.UserUpdateRequest;
import com.prettier.payload.response.concretes.UserResponse;
import com.prettier.repository.UserRepository;
import com.prettier.service.abstracts.UserService;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;

    //Not: getUserById() ******************************************************************************************************
    @Override
    public UserResponse getUserById(Language language, Long id) {
        return null;
    }

    //Not: updateUser() *******************************************************************************************************
    @Override
    public UserResponse update(Language language, UserUpdateRequest userUpdateRequest, Long id) {
        return null;
    }

    //Not: uploadProfileImage() ***********************************************************************************************
    @Override
    public UserResponse uploadUserProfileImage(Language language, Long id, MultipartFile file) {
        return null;
    }

    //Not: getProfileImage() **************************************************************************************************
    @Override
    public UserResponse getUserProfileImage(Language language, Long id) {
        return null;
    }

    //Not: deleteUser() *******************************************************************************************************
    @Override
    public UserResponse softDelete(Language language, Long id) {
        return null;
    }
}
