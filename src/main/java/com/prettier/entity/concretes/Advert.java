package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.abstracts.BaseEntity;
import com.prettier.entity.enums.AdvertStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "adverts")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class Advert extends BaseEntity implements Serializable {

    @Column(name = "title", nullable = false)
    @Size(min = 5, max = 150)
    private String title;

    @Column(name = "/desc/", nullable = false) //Sql icin desc ismine kontrol edilecek
    @Size(max = 300)
    private String desc;

    @Column(name = "slug", nullable = false)
    @Size(min = 5, max = 200)
    private String slug;

    @Column(name = "price", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    private AdvertStatus advertStatus; //!!! TODO: Default deger olarak "PENDING" atanacak

    @JsonIgnore
    private boolean builtIn;

    @JsonIgnore
    private boolean isActive=true;


    @Column(name = "view_count")
    private Integer viewCount = 0; //TODO: Service islemlerinde her görüntülendiginde degeri artacak

    @Column(name = "location")
    private String location;


    @OneToMany(mappedBy = "advert")
    @ToString.Exclude // Lazy Loading de performans kaybini önlemek icin spring tarafindan önerilen annotation
    private Set<Favorite> favoriteSet;

    public void setFavoriteSet(Set<Favorite> favoriteSet) {
        this.favoriteSet = favoriteSet;
    }

    public Set<Favorite> getFavoriteSet() {
        return favoriteSet;
    }

    //!!! TODO : ILISKILER EKLENECEK
}
