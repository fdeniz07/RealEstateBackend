package com.prettier.service.abstracts;

import com.prettier.payload.request.concretes.UserRoleChangeRequest;
import com.prettier.payload.request.concretes.UserUpdateRequest;
import com.prettier.payload.response.concretes.UserResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public interface UserService extends Serializable {

    UserResponse getUserById(Language language, Long id);

    UserResponse update(Language language, UserUpdateRequest userUpdateRequest, Long id);

    UserResponse uploadUserProfileImage(Language language, Long id, MultipartFile file);

    UserResponse getUserProfileImage(Language language, Long id);

    UserResponse softDelete(Language language, Long id);


}
