package com.prettier.payload.mapper;

import com.prettier.entity.concretes.Advert;
import com.prettier.payload.request.concretes.AdvertRequest;
import com.prettier.payload.request.concretes.AdvertUpdateRequest;
import com.prettier.payload.response.concretes.AdvertResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface AdvertMapper {

    AdvertMapper INSTANCE = Mappers.getMapper(AdvertMapper.class);

    Advert toAdvert(AdvertRequest advertRequest);

    AdvertResponse toResponse(Advert advert);

    Advert toUpdatedAdvert(AdvertUpdateRequest advertUpdateRequest, Long id);
    
    
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
