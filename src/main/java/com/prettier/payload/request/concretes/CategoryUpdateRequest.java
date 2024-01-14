package com.prettier.payload.request.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryUpdateRequest  extends BaseEntityRequest {

    @NotNull(message = "Please enter category title")
    @Size(min=2,max = 150, message = "Category title should be between 2 and 150 chars")
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
    private boolean active;

    @NotNull(message = "Please select advert")
    @Size(min=1, message ="Advert must not be empty")
    private Set<Advert> adverts;

    @NotNull(message = "Please select CategoryProperty")
    @Size(min=1, message ="CategoryProperty must not be empty")
    private Set<CategoryPropertyKey> categoryPropertyKeys;
}
