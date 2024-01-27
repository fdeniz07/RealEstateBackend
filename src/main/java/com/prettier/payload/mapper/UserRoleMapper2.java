package com.prettier.payload.mapper;

import com.prettier.entity.concretes.User;
import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.request.concretes.UserUpdateRequest;
import com.prettier.payload.response.concretes.UserResponse;

public class UserRoleMapper2 {


    public UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                //.userName(user.getUserName())
                .phone(user.getPhone())
                .isActive(user.isActive())
                .build();
    }

    public User toUser(UserRequest userRequest){

        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .username(userRequest.getUserName())
                .phone(userRequest.getPhone())
                .isActive(userRequest.isActive())
                .build();
    }


    public User toUpdatedUser(UserUpdateRequest userUpdateRequest, Long userId){

        return User.builder()
                .id(userId)
                .firstName(userUpdateRequest.getFirstName())
                .lastName(userUpdateRequest.getLastName())
                .email(userUpdateRequest.getEmail())
                .username(userUpdateRequest.getUserName())
                .phone(userUpdateRequest.getPhone())
                .isActive(userUpdateRequest.isActive())
                .build();
    }
}
