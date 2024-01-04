package com.prettier.payload.mapper;

import com.prettier.entity.concretes.District;
import com.prettier.payload.request.concretes.DistrictRequest;
import com.prettier.payload.response.concretes.DistrictResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface DistrictMapper {

    DistrictMapper INSTANCE = Mappers.getMapper(DistrictMapper.class);

    District toDistrict(DistrictRequest districtRequest);

    DistrictResponse toResponse(District district);


//    @Autowired
//    private ModelMapper modelMapper;
//
//    public District toDistrict(DistrictRequest districtRequest) {
//
//        return modelMapper.map(districtRequest, District.class);
//    }
//
//    public DistrictResponse toResponse(District district) {
//
//        return modelMapper.map(district, DistrictResponse.class);
//    }
}
