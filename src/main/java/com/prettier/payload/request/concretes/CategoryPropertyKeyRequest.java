package com.prettier.payload.request.concretes;

import com.prettier.payload.request.abstracts.BaseEntityRequest;
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
public class CategoryPropertyKeyRequest  extends BaseEntityRequest {

    @Size(min = 2, max = 80)
    private String name;

    private Long categoryId;
}
