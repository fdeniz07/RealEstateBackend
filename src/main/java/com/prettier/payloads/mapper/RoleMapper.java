package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.Role;
import com.prettier.payloads.request.concretes.RoleRequest;
import com.prettier.payloads.response.concretes.RoleResponse;
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
   RoleRequest toUpdatedRole(RoleRequest roleRequest, @MappingTarget RoleRequest existing);


}
