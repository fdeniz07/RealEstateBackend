package com.prettier.service.abstracts;

import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.request.concretes.UserUpdateRequest;
import com.prettier.payload.response.concretes.UserResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface UserService extends Serializable {

    Page<UserResponse> getUsersByActive(Language language, int page, int size, String sort, String type);

    Page<UserResponse> getUsers(Language language, int page, int size, String sort, String type);

    UserResponse getByUserId(Language language, Long id);

    UserResponse add(Language language, UserRequest userRequest);

    UserResponse update(Language language, UserUpdateRequest userUpdateRequest, Long id);

    UserResponse softDelete(Language language, Long id);


}
