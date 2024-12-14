package com.prettier.payloads.mapper;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payloads.request.concretes.UserRequestForAdmin;
import com.prettier.payloads.request.concretes.UserUpdateRequest;
import com.prettier.payloads.response.concretes.RoleResponse;
import com.prettier.payloads.response.concretes.UserResponseForAdmins;
import com.prettier.payloads.response.concretes.UserRoleChangeResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@Data
@RequiredArgsConstructor
public class UserMapperForAdmins {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
            user.passwordHash(passwordEncoder.encode(userRequest.getPasswordHash()));
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

            userResponse.roles(roleSetToRoleResponseSet(user.getRoles()));
        }
        return userResponse.build();
    }


    public UserRoleChangeResponse toRoleChangeResponse(User user) {

        UserRoleChangeResponse.UserRoleChangeResponseBuilder<?, ?> userResponse = UserRoleChangeResponse.builder();

        if (user != null) {
            userResponse.roles(roleSetToRoleResponseSet(user.getRoles()));
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


    protected Set<RoleResponse> roleSetToRoleResponseSet(Set<Role> set) {
        if (set == null) {
            return new HashSet<RoleResponse>();
        }

        Set<RoleResponse> set1 = new HashSet<RoleResponse>(Math.max((int) (set.size() / .75f) + 1, 16));
        for (Role role : set) {
            set1.add(roleMapper.toResponse(role));
        }

        return set1;
    }
}
