package com.prettier.config;

import com.prettier.payload.mapper.CategoryMapper;
import com.prettier.payload.mapper.ContactMapper;
import com.prettier.payload.mapper.FavoriteMapper;
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
    }@Bean

    public FavoriteMapper favoriteMapper(){
        return new FavoriteMapper();
    }
}
