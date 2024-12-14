package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payloads.request.concretes.SignUpRequest;
import com.prettier.payloads.response.concretes.SignUpResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapStructConfig.class,
        uses = {UserMapper.class,RoleMapper.class},
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

//    @Mapping(target = "id", source = "id")
//    @Mapping(target = "firstName", source = "firstName")
//    @Mapping(target = "lastName", source = "lastName")
//    @Mapping(target = "userName", source = "userName")
//    @Mapping(target = "email", source = "email")
//    @Mapping(target = "phone", source = "phone")
 //   @Mapping(target = "roles", source = "user.roles")
    SignUpResponse toResponse(User user);//,Role role

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "roles", source = "signUpRequest.roleIds", qualifiedByName = "longSetToRoleSet")
    User toUser(SignUpRequest signUpRequest);

    //@Mapping(target = "id", source = "userUpdateRequest")
    //UserRequest toUpdatedUser(UserUpdateRequest userUpdateRequest, @MappingTarget UserRequest existing);

    //List<UserResponse> toUserListResponse(List<User> users);

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

    @Named("stringSetToRoleSet")
    default Set<Role> stringSetToRoleSet(Set<String> roleIds) {
        return roleIds.stream()
                .map(roleName -> {
                    Role role = new Role();
                    role.setName(roleName);
                    return role;
                })
                .collect(Collectors.toSet());
    }


}
