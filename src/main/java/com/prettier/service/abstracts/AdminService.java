package com.prettier.service.abstracts;

import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.response.concretes.UserResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface AdminService  extends Serializable {

    Page<UserResponse> getAllUsersByActive(Language language, int page, int size, String sort, String type);

    Page<UserResponse> getAllUsers(Language language, int page, int size, String sort, String type);

    Page<UserResponse> getAllManagers(Language language, int page, int size, String sort, String type);

    Page<UserResponse> getAllCustomers(Language language, int page, int size, String sort, String type);

    UserResponse add(Language language, UserRequest userRequest);

    UserResponse changeUserRole(Language language, Long id);

    UserResponse changeUserStatus(Language language, Long id);

}
