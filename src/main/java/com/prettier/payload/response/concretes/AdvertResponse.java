package com.prettier.payload.response.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.entity.concretes.*;
import com.prettier.payload.response.abstracts.BaseEntityResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvertResponse extends BaseEntityResponse implements Serializable {

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
