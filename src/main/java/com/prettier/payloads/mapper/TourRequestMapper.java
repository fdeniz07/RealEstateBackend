package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.TourRequest;
import com.prettier.payloads.request.concretes.TourRequestRequest;
import com.prettier.payloads.request.concretes.TourRequestUpdateRequest;
import com.prettier.payloads.response.concretes.TourRequestResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(config = MapStructConfig.class,
        uses = {AdvertMapper.class},
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TourRequestMapper {

    TourRequestMapper INSTANCE = Mappers.getMapper(TourRequestMapper.class);

    TourRequestResponse toResponse(TourRequest tourRequest);

    TourRequest toTourRequest(TourRequestRequest tourRequestRequest);

    @Mapping(target = "advert", source = "tourRequestUpdateRequest")
    TourRequest toUpdatedTourRequest(TourRequestUpdateRequest tourRequestUpdateRequest, @MappingTarget TourRequest existing);


}
