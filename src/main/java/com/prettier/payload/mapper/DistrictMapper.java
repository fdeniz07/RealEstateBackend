package com.prettier.payload.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.City;
import com.prettier.entity.concretes.District;
import com.prettier.payload.request.concretes.CityUpdateRequest;
import com.prettier.payload.request.concretes.DistrictRequest;
import com.prettier.payload.request.concretes.DistrictUpdateRequest;
import com.prettier.payload.response.concretes.DistrictResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(config = MapStructConfig.class,
        uses = {CityMapper.class},
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DistrictMapper {

    DistrictMapper INSTANCE = Mappers.getMapper(DistrictMapper.class);

    DistrictResponse toResponse(District district);

    District toDistrict(DistrictRequest districtRequest);

    @Mapping(target = "city", source = "districtUpdateRequest")
    District toUpdatedDistrict(DistrictUpdateRequest districtUpdateRequest, @MappingTarget District existing);


}
