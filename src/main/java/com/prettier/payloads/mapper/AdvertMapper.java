package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.Advert;
import com.prettier.payloads.request.concretes.AdvertRequest;
import com.prettier.payloads.request.concretes.AdvertUpdateRequest;
import com.prettier.payloads.response.concretes.AdvertResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(config = MapStructConfig.class)
public interface AdvertMapper {

    AdvertMapper INSTANCE = Mappers.getMapper(AdvertMapper.class);

    Advert toAdvert(AdvertRequest advertRequest);

    AdvertResponse toResponse(Advert advert);

    @Mapping(target = "id", ignore = true)
    Advert toUpdatedAdvert(AdvertUpdateRequest advertUpdateRequest, Long id, @MappingTarget Advert advert);
    
    
//    public Advert toAdvert(AdvertRequest advertRequest) {
//
//        return modelMapper.map(advertRequest, Advert.class);
//    }
//
//    public Advert toUpdatedAdvert(Language language, AdvertUpdateRequest advertUpdateRequest, Long id) {
//
//        return modelMapper.map(advertUpdateRequest, Advert.class);
//    }
//
//    public AdvertResponse toResponse(Advert advert) {
//
//        return modelMapper.map(advert, AdvertResponse.class);
//    }
}
