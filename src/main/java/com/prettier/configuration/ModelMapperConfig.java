package com.prettier.configuration;
import com.prettier.entity.abstracts.BaseEntity;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Genel konfigürasyon ayarları
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT) // Eşleme stratejisi belirleniyor
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE); // Erişim seviyesi belirleniyor

        //MatchingStrategies.STRICT kullanılarak modelmapper'ın sıkı bir eşleme stratejisi benimsemesi sağlanmıştır. Bu strateji, eşleme yaparken null değerleri atlamak için kullanılabilir. Ayrıca, setFieldAccessLevel(Configuration.AccessLevel.PRIVATE) ile private alanlara erişim sağlanmıştır.

        // Özel PropertyMap ayarı
        modelMapper.addMappings(new PropertyMap<BaseEntityRequest, BaseEntity>() {
            @Override
            protected void configure() {
                // Boolean alanlar için özelleştirme
               // map().setIsActive(source.isActive()); // isActive true olduğunda set edilir
                map().setDeleted(source.isDeleted()); // isDeleted true olduğunda set edilir
            }
        });
        return modelMapper;
    }
}

