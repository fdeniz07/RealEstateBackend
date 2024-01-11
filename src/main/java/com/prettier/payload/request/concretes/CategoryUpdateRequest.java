package com.prettier.payload.request.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
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
    @Size(max = 150)
    private String title;

    private String icon;

    private boolean builtIn;

    private int seq;

    @Size(min = 5, max = 200)
    private String slug;

    private boolean active;

    private Set<Advert> adverts;

    private Set<CategoryPropertyKey> categoryPropertyKeys;
}
