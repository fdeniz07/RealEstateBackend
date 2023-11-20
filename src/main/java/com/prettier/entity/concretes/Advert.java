package com.prettier.entity.concretes;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Set;

public class Advert {
    // TODO: 11/20/2023 yapilacak

    @OneToMany(mappedBy = "advert")
    private Set<Favorite> favoriteSet;
}
