package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "cities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City extends BaseEntity{

    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 50)
    private String name;

    @Column(name = "Plate Number", nullable = false)
    private Integer plateNumber;

    @ManyToOne//(cascade = CascadeType.ALL) // cascade = CascadeType.ALL ifadesi, District sınıfının kaydedilmesi durumunda ilişkili Country nesnesinin de otomatik olarak kaydedilmesini sağlar.
    @JoinColumn(name = "country_id")
   // @JsonIgnore //coklu iliskilerde tablonun birinde bu annotation kullanilir, aksi durumda sout yapildiginda sonsuz döngüye girer!
    //@JsonIgnoreProperties("country")
    private Country country;

    @JsonIgnore
    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE)
    private Set<District> districtSet;

    @JsonIgnore
    @OneToMany(mappedBy = "city", cascade = CascadeType.REMOVE)
    private Set<Advert> advertSet;
}
