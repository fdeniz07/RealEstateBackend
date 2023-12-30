package com.prettier.payload.mapper;

import com.prettier.entity.concretes.City;
import com.prettier.payload.request.concretes.CityRequest;
import com.prettier.payload.response.concretes.CityResponse;
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
public class CityMapper {

    @Autowired
    private  ModelMapper modelMapper;

    public City toCity(CityRequest cityRequest) {
        return modelMapper.map(cityRequest, City.class);
    }

        public CityResponse toResponse(City city) {
        return modelMapper.map(city, CityResponse.class);
    }
}
