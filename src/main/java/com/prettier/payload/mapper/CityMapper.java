package com.prettier.payload.mapper;

import com.prettier.entity.concretes.City;
import com.prettier.payload.request.concretes.CityRequest;
import com.prettier.payload.response.concretes.CityResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityMapper {

    private final ModelMapper modelMapper;

    public City toCity(CityRequest cityRequest) {
        return modelMapper.map(cityRequest, City.class);
    }

        public CityResponse toResponse(City city) {
        return modelMapper.map(city, CityResponse.class);
    }
}
