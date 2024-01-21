package com.prettier.payload.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.request.concretes.RoleRequest;
import com.prettier.payload.request.concretes.RoleUpdateRequest;
import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.request.concretes.UserUpdateRequest;
import com.prettier.payload.response.concretes.RoleResponse;
import com.prettier.payload.response.concretes.UserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(config = MapStructConfig.class,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleResponse toResponse(Role role);

    @Mapping(target = "name",source = "roleName")
    Role toRole(RoleRequest roleRequest);

    //@Mapping(target = "id", source = "userUpdateRequest")
   RoleRequest toUpdatedRole(RoleUpdateRequest roleUpdateRequest, @MappingTarget RoleRequest existing);


}
