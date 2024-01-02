package com.prettier.payload.mapper;

import com.prettier.entity.concretes.Country;
import com.prettier.payload.request.concretes.CountryRequest;
import com.prettier.payload.response.concretes.CountryResponse;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Country toCountry(CountryRequest countryRequest) {

        return modelMapper.map(countryRequest, Country.class);
    }

    public CountryResponse toResponse(Country country) {

        return modelMapper.map(country, CountryResponse.class);
    }
}
