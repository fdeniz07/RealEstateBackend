package com.prettier.payload.mapper;

import com.prettier.entity.concretes.Country;
import com.prettier.payload.request.concretes.CountryRequest;
import com.prettier.payload.response.concretes.CountryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    Country toCountry(CountryRequest countryRequest);

    CountryResponse toResponse(Country country);



//    @Autowired
//    private ModelMapper modelMapper;
//
//    public Country toCountry(CountryRequest countryRequest) {
//
//        return modelMapper.map(countryRequest, Country.class);
//    }
//
//    public CountryResponse toResponse(Country country) {
//
//        return modelMapper.map(country, CountryResponse.class);
//    }
}
