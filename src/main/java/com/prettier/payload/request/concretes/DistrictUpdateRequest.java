package com.prettier.payload.request.concretes;

import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DistrictUpdateRequest extends BaseEntityRequest {

    @Size(min = 2, max = 50)
    private String name;

    private Long cityId;

}
