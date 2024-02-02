package com.prettier.payload.mapper;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.request.concretes.UserRequestForAdmin;
import com.prettier.payload.request.concretes.UserUpdateRequest;
import com.prettier.payload.response.concretes.UserResponseForAdmins;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;


//@Mapper(config = MapStructConfig.class)
@Data
@RequiredArgsConstructor
public class UserMapperForAdmins {

//    UserMapperForAdmins INSTANCE = Mappers.getMapper(UserMapperForAdmins.class);
//
//    UserResponseForAdmins toResponse(User user);
//
//    User toUser(UserRequest userRequest);
//
////    @Mapping(target = "user", source = "userUpdateRequest")
//    User toUpdatedUser(UserUpdateRequest userUpdateRequest, @MappingTarget User existing);


    public User toUser(UserRequestForAdmin userRequest) {

        User.UserBuilder<?, ?> user = User.builder();

        if (userRequest != null) {
            user.id(userRequest.getId());
            user.createAt(userRequest.getCreateAt());
            user.updateAt(userRequest.getUpdateAt());
            user.deleted(userRequest.isDeleted());
            user.firstName(userRequest.getFirstName());
            user.lastName(userRequest.getLastName());
            user.email(userRequest.getEmail());
            user.phone(userRequest.getPhone());
            user.gender(userRequest.getGender());
            user.birthDate(userRequest.getBirthDate());
            user.passwordHash(userRequest.getPasswordHash());
            user.gender(userRequest.getGender());
            user.userInfo(userRequest.getUserInfo());
            user.image(userRequest.getImage());
        }
        return user.build();
    }


    public UserResponseForAdmins toResponse(User user) {

        UserResponseForAdmins.UserResponseForAdminsBuilder<?, ?> userResponse = UserResponseForAdmins.builder();

        if (user != null) {
            userResponse.id(user.getId());
            userResponse.createAt(user.getCreateAt());
            userResponse.updateAt(user.getUpdateAt());
            userResponse.deleted(user.isDeleted());
            userResponse.firstName(user.getFirstName());
            userResponse.lastName(user.getLastName());
            userResponse.email(user.getEmail());
            userResponse.phone(user.getPhone());
            userResponse.gender(user.getGender());
            userResponse.birthDate(user.getBirthDate());
            userResponse.image(user.getImage());
            userResponse.userInfo(user.getUserInfo());
            userResponse.resetPasswordCode(user.getResetPasswordCode());
            userResponse.builtIn(user.isBuiltIn());
            userResponse.active(user.isActive());
            userResponse.activationToken(user.getActivationToken());
        }
        return userResponse.build();
    }

    public User toUpdatedUser(UserUpdateRequest userUpdateRequest, User existing) {

        if (userUpdateRequest != null) {
            if (userUpdateRequest.getId() != null) {
                existing.setId(userUpdateRequest.getId());
            }
            if (userUpdateRequest.getCreateAt() != null) {
                existing.setCreateAt(userUpdateRequest.getCreateAt());
            }
            if (userUpdateRequest.getUpdateAt() != null) {
                existing.setUpdateAt(userUpdateRequest.getUpdateAt());
            }
            existing.setDeleted(userUpdateRequest.isDeleted());
            if (userUpdateRequest.getFirstName() != null) {
                existing.setFirstName(userUpdateRequest.getFirstName());
            }
            if (userUpdateRequest.getLastName() != null) {
                existing.setLastName(userUpdateRequest.getLastName());
            }
            if (userUpdateRequest.getEmail() != null) {
                existing.setEmail(userUpdateRequest.getEmail());
            }
            if (userUpdateRequest.getPhone() != null) {
                existing.setPhone(userUpdateRequest.getPhone());
            }
            if (userUpdateRequest.getGender() != null) {
                existing.setGender(userUpdateRequest.getGender());
            }
            if (userUpdateRequest.getBirthDate() != null) {
                existing.setBirthDate(userUpdateRequest.getBirthDate());
            }
            if (userUpdateRequest.getPasswordHash() != null) {
                existing.setPasswordHash(userUpdateRequest.getPasswordHash());
            }
            if (userUpdateRequest.getImage() != null) {
                existing.setImage(userUpdateRequest.getImage());
            }
            if (userUpdateRequest.getUserInfo() != null) {
                existing.setUserInfo(userUpdateRequest.getUserInfo());
            }
            //TODO: Social Media will be added after
        }

        return existing;
    }
}
