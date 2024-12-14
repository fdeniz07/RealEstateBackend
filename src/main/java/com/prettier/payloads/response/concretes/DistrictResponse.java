package com.prettier.payloads.response.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.payloads.response.abstracts.BaseEntityResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistrictResponse extends BaseEntityResponse {

    private String name;
    private CityResponse city;
}
