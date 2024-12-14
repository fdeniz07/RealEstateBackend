package com.prettier.payloads.response.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.entity.concretes.*;
import com.prettier.payloads.response.abstracts.BaseEntityResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvertResponse extends BaseEntityResponse{

    private String title;
    private String desc;
    private boolean isActive;
    private Double price;
    private AdvertType advertType;
    private Country country;
    private City city;
    private District district;
    private Category category;
    private Set<Image> imageSet;
    private Set<CategoryPropertyValue> categoryPropertyValueSet;
    private String location;

}
