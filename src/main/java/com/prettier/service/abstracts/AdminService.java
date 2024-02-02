package com.prettier.service.abstracts;

import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.request.concretes.UserRequestForAdmin;
import com.prettier.payload.response.concretes.UserResponseForAdmins;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface AdminService  extends Serializable {

    Page<UserResponseForAdmins> getAllUsers(Language language, int page, int size, String sort, String type);

    Page<UserResponseForAdmins> getAllUsersByActive(Language language, int page, int size, String sort, String type);

    Page<UserResponseForAdmins> getAllUsersByInactive(Language language, int page, int size, String sort, String type);

    Page<UserResponseForAdmins> getAllManagers(Language language, int page, int size, String sort, String type);

    Page<UserResponseForAdmins> getAllCustomers(Language language, int page, int size, String sort, String type);

    UserResponseForAdmins add(Language language, UserRequestForAdmin userRequest);

    UserResponseForAdmins changeUserRole(Language language, Long id);

    UserResponseForAdmins changeUserStatus(Language language, Long id);

}
