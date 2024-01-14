package com.prettier.payload.request.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
//@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class CityUpdateRequest extends BaseEntityRequest{

    @NotNull(message = "Please enter city name")
    @Size(min = 2, max = 50, message = "City name should be between 2 and 50 chars")
    private String name;

    @NotNull(message = "Please select country")
    private CountryRequest country;
}
