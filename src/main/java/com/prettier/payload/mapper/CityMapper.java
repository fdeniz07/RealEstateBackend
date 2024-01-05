package com.prettier.payload.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.City;
import com.prettier.payload.request.concretes.CityRequest;
import com.prettier.payload.request.concretes.CityUpdateRequest;
import com.prettier.payload.response.concretes.CityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    City toCity(CityRequest cityRequest);

    CityResponse toResponse(City city);

    City toUpdatedCity(CityUpdateRequest cityUpdateRequest,Long id);


//    public City toCity(CityRequest cityRequest) {
//
//        return modelMapper.map(cityRequest, City.class);
//    }
//
//    public CityResponse toResponse(City city) {
//
//        return modelMapper.map(city, CityResponse.class);
//    }
//
//    public City toUpdatedCity(CityUpdateRequest cityUpdateRequest, City existingCity) {
//
//        modelMapper.map(cityUpdateRequest, existingCity);
//        return existingCity;
//    }


}
