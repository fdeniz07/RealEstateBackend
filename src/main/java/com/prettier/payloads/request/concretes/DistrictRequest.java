package com.prettier.payloads.request.concretes;

import com.prettier.payloads.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DistrictRequest extends BaseEntityRequest{

    @NotNull(message = "Please enter district name")
    @Size(min = 2, max = 50, message = "District name should be between 2 and 150 chars")
    private String name;

    @NotNull(message = "Please select city")
    @Size(min=1, message ="City must not be empty")
    private Long cityId;
}
