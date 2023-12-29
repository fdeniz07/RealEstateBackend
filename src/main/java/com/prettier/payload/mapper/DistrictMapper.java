package com.prettier.payload.mapper;

import com.prettier.entity.concretes.District;
import com.prettier.payload.request.concretes.DistrictRequest;
import com.prettier.payload.response.concretes.DistrictResponse;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistrictMapper {

    private ModelMapper modelMapper;

    public District toDistrict(DistrictRequest districtRequest) {
        return modelMapper.map(districtRequest, District.class);
    }

    public DistrictResponse toResponse(District district) {
        return modelMapper.map(district, DistrictResponse.class);
    }
}
