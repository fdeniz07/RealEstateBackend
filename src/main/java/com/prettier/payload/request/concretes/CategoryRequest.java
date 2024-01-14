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

    @NotNull(message = "Please enter title")
    @Size(min=2, max = 150, message = "Your title should be between 2 and 150 chars")
    private String title;

    @NotNull(message = "Please enter icon path")
    @Size(max = 50, message = "Your icon path should be between 1 and 50 chars")
    private String icon;

    private boolean builtIn;

    @NotNull(message = "Please enter seq")
    private int seq;

    @NotNull(message = "Please enter slug")
    @Size(min = 5, max = 200, message = "Your icon slug should be between 5 and 200 chars")
    private String slug;

    @NotNull(message = "Please enter status")
    private boolean active=true;
}