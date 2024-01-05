package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    @JsonIgnore //coklu iliskilerde tablonun birinde bu annotation kullanilir, aksi durumda sout yapildiginda sonsuz döngüye girer!
    private Country country;

    @OneToMany(mappedBy = "city")
    private Set<District> districtSet;

    @OneToMany(mappedBy = "city")
    private Set<Advert> advertSet;
}
