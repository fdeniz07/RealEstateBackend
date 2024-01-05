package com.prettier.payload.request.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.CategoryPropertyKey;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class CategoryUpdateRequest {
    @Size(max = 150)
    private String title;

    private String icon;

    private boolean builtIn;

    private int seq;

    @Size(min = 5, max = 200)
    private String slug;

    private boolean active;

//    private Set<Advert> advertSet;
//
//    private Set<CategoryPropertyKey> categoryPropertyKeys;
}
