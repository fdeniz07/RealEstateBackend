package com.prettier.config;

import org.mapstruct.ReportingPolicy;
import org.mapstruct.MapperConfig;

@MapperConfig(
        componentModel = "spring", //Bu sınıf spring component modelini kullanarak Spring ile entegrasyonu sağlar
        unmappedTargetPolicy = ReportingPolicy.IGNORE //unmappedTargetPolicy özelliği ile eşleşmeyen hedef özelliklere ilişkin politikayı belirler.
)
public interface MapStructConfig {

}
