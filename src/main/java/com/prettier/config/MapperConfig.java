package com.prettier.config;

import com.prettier.payload.mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    // *************   MAPSTRUCT MAPPING BEANS   ***************************************************************

    @Bean
    public AdvertMapper advertMapper() {

        return new AdvertMapperImpl();
    }

    @Bean
    public CategoryMapper categoryMapper() {

        return new CategoryMapperImpl();
    }

    @Bean
    public CityMapper cityMapper() {

        return new CityMapperImpl();
    }

    @Bean
    public CountryMapper countryMapper() {

        return new CountryMapperImpl();
    }

    @Bean
    public DistrictMapper districtMapper() {

        return new DistrictMapperImpl();
    }

    @Bean
    public FavoriteMapper favoriteMapper() {

        return new FavoriteMapperImpl();
    }

    @Bean
    public CategoryPropertyKeyMapper categoryPropertyKeyMapper() {

        return new CategoryPropertyKeyMapperImpl();
    }

    @Bean
    public TourRequestMapper tourRequestMapper() {

        return new TourRequestMapperImpl();
    }

    @Bean
    public UserMapper userMapper() {

        return new UserMapperImpl();
    }

    @Bean
    public RoleMapper roleMapper() {

        return new RoleMapperImpl();
    }

    @Bean
    public UserRoleMapper userRoleMapper() {

        return new UserRoleMapperImpl();
    }

    @Bean
    public AuthMapper authMapper() {

        return new AuthMapperImpl();
    }

    @Bean
    public MessageMapper messageMapper() {

        return new MessageMapper() {
        };
    }


    // *************   MANUEL MAPPING BEANS   ***************************************************************

    @Bean
    public UserMapperForAdmins userMapperForAdmins() {

        return new UserMapperForAdmins();
    }
}
