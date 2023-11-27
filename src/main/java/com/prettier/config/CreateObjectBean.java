package com.prettier.config;

import com.prettier.payload.mapper.ContactMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateObjectBean {

    @Bean
    public ContactMapper contactMapper(){
        return new ContactMapper();
    }
}
