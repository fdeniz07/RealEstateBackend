package com.prettier.payloads.request.concretes;

import com.prettier.payloads.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CategoryPropertyKeyUpdateRequest  extends BaseEntityRequest {

    @Size(min = 2, max = 80)
    private String name;

    private Long categoryId;

}
