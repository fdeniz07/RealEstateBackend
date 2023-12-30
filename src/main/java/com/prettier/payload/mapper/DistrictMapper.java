package com.prettier.payload.mapper;

import com.prettier.entity.concretes.District;
import com.prettier.payload.request.concretes.DistrictRequest;
import com.prettier.payload.response.concretes.DistrictResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistrictMapper {

    @Autowired
    private ModelMapper modelMapper;

    public District toDistrict(DistrictRequest districtRequest) {
        return modelMapper.map(districtRequest, District.class);
    }

    public DistrictResponse toResponse(District district) {
        return modelMapper.map(district, DistrictResponse.class);
    }
}
