package com.prettier.entity.concretes;

import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "countries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Country extends BaseEntity{

    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 50)
    private String name;

    @Column(name = "code", nullable = false)
    @Size(min = 1, max = 3)
    private String code;


    @OneToMany(mappedBy = "country")
    private Set<City> citySet;

    @OneToMany(mappedBy = "country")
    private Set<Advert> advertSet;
}
