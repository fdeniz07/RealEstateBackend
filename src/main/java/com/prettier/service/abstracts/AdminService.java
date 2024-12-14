package com.prettier.service.abstracts;

import com.prettier.payloads.request.concretes.UserRequestForAdmin;
import com.prettier.payloads.request.concretes.UserRoleChangeRequest;
import com.prettier.payloads.response.concretes.UserResponseForAdmins;
import com.prettier.payloads.response.concretes.UserRoleChangeResponse;
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

    UserRoleChangeResponse changeUserRole(Language language, UserRoleChangeRequest request,Long id);

    UserResponseForAdmins changeUserStatus(Language language, Long id);

}
