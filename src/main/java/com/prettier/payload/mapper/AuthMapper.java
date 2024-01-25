package com.prettier.payload.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.City;
import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.request.concretes.*;
import com.prettier.payload.response.concretes.CityResponse;
import com.prettier.payload.response.concretes.SignUpResponse;
import com.prettier.payload.response.concretes.UserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapStructConfig.class,
        uses = {UserMapper.class,RoleMapper.class},
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    @Mapping(target = "id", source = "user.id")
    SignUpResponse toResponse(User user);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "roles", source = "signUpRequest.roleIds", qualifiedByName = "longSetToRoleSet")
//    User toUser(SignUpRequest signUpRequest);

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


}
