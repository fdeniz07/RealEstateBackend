package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "districts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class District extends BaseEntity{

    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 50)
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST) // cascade = CascadeType.ALL ifadesi, District sınıfının kaydedilmesi durumunda ilişkili City nesnesinin de otomatik olarak kaydedilmesini sağlar.
    @JoinColumn(name = "city_id")
    @JsonIgnore    //coklu iliskilerde tablonun birinde bu annotation kullanilir, aksi durumda sout yapildiginda sonsuz döngüye girer!
    @JsonIgnoreProperties("city")
    private City city;

    @OneToMany(mappedBy = "district", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Advert> advertSet;
}
