package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.User;
import com.prettier.payloads.request.concretes.UserRequest;
import com.prettier.payloads.request.concretes.UserUpdateRequest;
import com.prettier.payloads.response.concretes.UserResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(config = MapStructConfig.class,
        uses = {AdvertMapper.class},
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toResponse(User user);

    User toUser(UserRequest userRequest);

//    @Mapping(target = "user", source = "userUpdateRequest")
    User toUpdatedUser(UserUpdateRequest userUpdateRequest, @MappingTarget User existing);


}
