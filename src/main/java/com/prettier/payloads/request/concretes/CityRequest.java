package com.prettier.payloads.request.concretes;

import com.prettier.payloads.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CityRequest extends BaseEntityRequest {

    @NotNull(message = "Please enter city name")
    @Size(min = 2, max = 50, message = "City name should be between 2 and 50 chars")
    private String name;

    @NotNull(message = "Please select country")
    private CountryRequest country;
}
