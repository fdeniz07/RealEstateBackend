package com.prettier.config;

import com.prettier.payload.mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateObjectBean {

    @Bean
    public ContactMapper contactMapper(){
        return new ContactMapper();
    }

    @Bean
    public CategoryMapper categoryMapper(){
        return new CategoryMapper();
    }

    @Bean
    public FavoriteMapper favoriteMapper(){
        return new FavoriteMapper();
    }

    @Bean
    public CountryMapper countryMapper(){
        return new CountryMapper();
    }

    @Bean
    public CityMapper cityMapper(){
        return new CityMapper();
    }

    @Bean
    public DistrictMapper districtMapper(){
        return new DistrictMapper();
    }

    @Bean
    public AdvertMapper advertMapper(){
        return new AdvertMapper();
    }
}
