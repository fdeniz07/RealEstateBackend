package com.prettier.payload.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.City;
import com.prettier.payload.request.concretes.CityRequest;
import com.prettier.payload.request.concretes.CityUpdateRequest;
import com.prettier.payload.response.concretes.CityResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class,
        uses = {CountryMapper.class},
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//CityMapper'ın uses özelliği sayesinde, CityMapper sınıfının içinde CountryMapper'ı kullanabiliriz. Bu, iç içe olan DTO ve domain nesneleri arasındaki dönüşümleri kolaylaştırır.
//NullValueMappingStrategy.RETURN_DEFAULT kullanılmıştır. Bu strateji, hedef nesne için varsayılan değeri kullanarak null değerleri döndürecektir. Bu sayede, kaynak nesnesinde null olan alanlar, hedef nesnede varsayılan değer ile doldurulacaktır.
//NullValuePropertyMappingStrategy.IGNORE kullanılmıştır. Bu strateji, kaynak nesnesindeki bir alanın değeri null ise, o alanın hedef nesneye haritalanmasını tamamen atlar.
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(target = "country", source = "cityRequest.country")
    City toCity(CityRequest cityRequest);

    CityResponse toResponse(City city);

    @Mapping(target = "country", source = "cityUpdateRequest.country")
    City toUpdatedCity(CityUpdateRequest cityUpdateRequest, @MappingTarget City existing);


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
