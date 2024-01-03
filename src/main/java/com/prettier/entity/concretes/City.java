package com.prettier.entity.concretes;

import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "cities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true) // normalde toString metodu ilgili sinifin field'larini yazririr, callsuper ile basedeki fieldlari da yazdirmaya yarar
public class City extends BaseEntity implements Serializable {

    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "city")
    private Set<District> districtSet;

    @OneToMany(mappedBy = "city")
    private Set<Advert> advertSet;
}
