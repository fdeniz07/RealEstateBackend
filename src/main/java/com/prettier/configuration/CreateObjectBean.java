package com.prettier.configuration;

import com.prettier.payload.mapper.*;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateObjectBean {

    @Bean
    public AdvertMapper advertMapper() {
        return Mappers.getMapper(AdvertMapper.class);
    }

        @Bean
    public ContactMapper contactMapper(){
        return Mappers.getMapper(ContactMapper.class);
    }

    @Bean
    public CategoryMapper categoryMapper(){
        return Mappers.getMapper(CategoryMapper.class);
    }

    @Bean
    public FavoriteMapper favoriteMapper(){
        return Mappers.getMapper(FavoriteMapper.class);
    }

    @Bean
    public CountryMapper countryMapper(){
        return Mappers.getMapper(CountryMapper.class);
    }

    @Bean
    public CityMapper cityMapper(){
        return Mappers.getMapper(CityMapper.class);
    }

    @Bean
    public DistrictMapper districtMapper(){
        return Mappers.getMapper(DistrictMapper.class);
    }




//    @Bean
//    public ContactMapper contactMapper(){
//        return new ContactMapper();
//    }
//
//    @Bean
//    public CategoryMapper categoryMapper(){
//        return new CategoryMapper();
//    }
//
//    @Bean
//    public FavoriteMapper favoriteMapper(){
//        return new FavoriteMapper();
//    }
//
//    @Bean
//    public CountryMapper countryMapper(){
//        return new CountryMapper();
//    }
//
//    @Bean
//    public CityMapper cityMapper(){
//        return new CityMapper();
//    }
//
//    @Bean
//    public DistrictMapper districtMapper(){
//        return new DistrictMapper();
//    }
//
//    @Bean
//    public AdvertMapper advertMapper(){
//        return new AdvertMapper();
//    }
}
