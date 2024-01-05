package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    @JsonIgnore    //coklu iliskilerde tablonun birinde bu annotation kullanilir, aksi durumda sout yapildiginda sonsuz döngüye girer!
    private City city;

    @OneToMany(mappedBy = "district")
    private Set<Advert> advertSet;


}
