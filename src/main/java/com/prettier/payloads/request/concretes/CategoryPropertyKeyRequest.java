package com.prettier.payloads.request.concretes;

import com.prettier.payloads.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Please enter category property name")
    @Size(min = 2, max = 80, message = "Category property name should be between 2 and 80 chars")
    private String name;

    @NotNull(message = "Please select category")
    @Size(min=1, message ="Category must not be empty")
    private Long categoryId;
}
