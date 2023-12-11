package com.prettier.payload.response.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payload.response.abstracts.BaseEntityResponse;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Set;

public class CategoryResponse extends BaseEntityResponse implements Serializable {

    private String title;
    private String icon;
    private boolean builtIn;
    private int seq;
    private String slug;
    private boolean isActive=true;
    private Set<Advert> advertSet;
    private Set<CategoryPropertyKey> categoryPropertyKeys;
    //
}
