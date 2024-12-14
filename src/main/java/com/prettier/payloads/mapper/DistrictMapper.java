package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.District;
import com.prettier.payloads.request.concretes.DistrictRequest;
import com.prettier.payloads.request.concretes.DistrictUpdateRequest;
import com.prettier.payloads.response.concretes.DistrictResponse;
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
