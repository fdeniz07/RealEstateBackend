package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payloads.request.concretes.UserRequest;
import com.prettier.payloads.request.concretes.UserUpdateRequest;
import com.prettier.payloads.response.concretes.UserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(config = MapStructConfig.class,
        componentModel = "spring")
public interface UserRoleMapper {

    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    @Mapping(target = "id", source = "user.id")
    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "roles", source = "userRequest.roleIds", qualifiedByName = "longSetToRoleSet")
    User toUser(UserRequest userRequest);

    //@Mapping(target = "id", source = "userUpdateRequest")
    UserRequest toUpdatedUser(UserUpdateRequest userUpdateRequest, @MappingTarget UserRequest existing);

    List<UserResponse> toUserListResponse(List<User> users);

    @Named("longSetToRoleSet")
    default Set<Role> longSetToRoleSet(Set<Long> roleIds) {
        return roleIds.stream()
                .map(roleId -> {
                    Role role = new Role();
                    role.setId(roleId);
                    return role;
                })
                .collect(Collectors.toSet());
    }
}
