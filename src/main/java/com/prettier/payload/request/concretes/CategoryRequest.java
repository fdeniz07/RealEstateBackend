package com.prettier.payload.request.concretes;

import com.prettier.payload.request.abstracts.BaseEntityRequest;
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
public class CategoryRequest extends BaseEntityRequest{

    @NotNull
    @Size(max = 150)
    private String title;

    @NotNull
    @Size(max = 50)
    private String icon;

    private boolean builtIn;

    @NotNull
    private int seq;

    @NotNull
    @Size(min = 5, max = 200)
    private String slug;

    @NotNull
    private boolean active=true;
}