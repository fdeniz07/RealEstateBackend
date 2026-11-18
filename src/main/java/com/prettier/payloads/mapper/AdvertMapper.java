package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.Advert;
import com.prettier.payloads.request.concretes.AdvertCreateRequest;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "advertStatus", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "builtIn", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "advertType", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "district", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "favoriteSet", ignore = true)
    @Mapping(target = "imageSet", ignore = true)
    @Mapping(target = "tourRequestSet", ignore = true)
    @Mapping(target = "logSet", ignore = true)
    @Mapping(target = "categoryPropertyValueSet", ignore = true)
    Advert toAdvert(AdvertCreateRequest advertCreateRequest);

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
