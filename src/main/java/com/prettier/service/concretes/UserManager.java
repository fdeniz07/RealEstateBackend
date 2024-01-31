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


@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;


    @Override
    public Page<UserResponse> getUsersByActive(Language language, int page, int size, String sort, String type) {
        return null;
    }

    @Override
    public Page<UserResponse> getUsers(Language language, int page, int size, String sort, String type) {
        return null;
    }

    @Override
    public UserResponse getByUserId(Language language, Long id) {
        return null;
    }

    @Override
    public UserResponse add(Language language, UserRequest userRequest) {
        return null;
    }

    @Override
    public UserResponse update(Language language, UserUpdateRequest userUpdateRequest, Long id) {
        return null;
    }

    @Override
    public UserResponse softDelete(Language language, Long id) {
        return null;
    }
}
