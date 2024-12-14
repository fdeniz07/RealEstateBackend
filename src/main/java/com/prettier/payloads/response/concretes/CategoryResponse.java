package com.prettier.payloads.response.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payloads.response.abstracts.BaseEntityResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse extends BaseEntityResponse implements Serializable {

    private String title;
    private String icon;
    private boolean builtIn;
    private int seq;
    private String slug;
    private boolean active;
    private Set<Advert> advertSet;
    private Set<CategoryPropertyKey> categoryPropertyKeys;
}