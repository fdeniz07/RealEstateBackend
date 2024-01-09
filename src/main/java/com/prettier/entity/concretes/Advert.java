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
public class Advert extends BaseEntity{

    @Column(name = "title", nullable = false)
    @Size(min = 5, max = 150)
    private String title;

    @Column(name = "\"desc\"", nullable = true)
    @Size(max = 300)
    private String desc;

    @Column(name = "slug", nullable = false)
    @Size(min = 5, max = 200)
    private String slug; //!!! TODO: During Mapping must be title with Url encoded !!!

    @Column(name = "price", nullable = false)
    private Double price;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private AdvertStatus advertStatus =AdvertStatus.PENDING;

    @JsonIgnore
    private boolean builtIn;

    @JsonIgnore
    private boolean isActive=true;

    @Column(name = "view_count")
    private Integer viewCount = 0; //!!! TODO: Service islemlerinde her görüntülendiginde degeri artacak !!!

    @Column(name = "location")
    private String location;

    //!!! TODO : ILISKILER EKLENECEK

    @ManyToOne
    @JoinColumn(name = "advert_type_id", nullable = false)
    @JsonIgnore //coklu iliskilerde tablonun birinde bu annotation kullanilir, aksi durumda sout yapildiginda sonsuz döngüye girer!
    private AdvertType advertType;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    @JsonIgnore
    private Country country;


    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    @JsonIgnore
    private City city;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    @JsonIgnore
    private District district;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "advert")
    @ToString.Exclude //!!! Note: Lazy Loading de performans kaybini önlemek icin spring tarafindan önerilen annotation
    private Set<Favorite> favoriteSet;

    @OneToMany(mappedBy = "advert")
    @ToString.Exclude
    private Set<Image> imageSet;

    @OneToMany(mappedBy = "advert")
    @ToString.Exclude
    private Set<TourRequest> tourRequestSet;

    @OneToMany(mappedBy = "advert")
    @ToString.Exclude
    private Set<Log> logSet;

    @OneToMany(mappedBy = "advert")
    @ToString.Exclude
    private Set<CategoryPropertyValue> categoryPropertyValueSet;
}
