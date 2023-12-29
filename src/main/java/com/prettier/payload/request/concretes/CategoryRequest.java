package com.prettier.payload.request.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@SuperBuilder
public class CategoryRequest extends BaseEntityRequest implements Serializable {

    @Size(max = 150)
    private String title;

    private String icon;

    private boolean builtIn;

    private int seq;

    @Size(min = 5, max = 200)
    private String slug;

    private boolean isActive = true;

    private String test;

    private Set<Advert> advertSet;

    private Set<CategoryPropertyKey> categoryPropertyKeys;
}
