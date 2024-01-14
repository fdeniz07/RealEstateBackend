package com.prettier.payload.request.concretes;

import com.prettier.payload.request.abstracts.BaseEntityRequest;
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
public class CountryUpdateRequest extends BaseEntityRequest{

    @NotNull(message = "Please enter country name")
    @Size(min = 2, max = 50, message = "Country name should be between 2 and 150 chars")
    private String name;

}
