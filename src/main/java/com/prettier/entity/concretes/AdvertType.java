package com.prettier.entity.concretes;

import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "advert_types")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AdvertType extends BaseEntity{

    @Column(name = "title", nullable = false)
    @Size(max = 30)
    private String title;

    @OneToMany(mappedBy = "advertType")
    @ToString.Exclude
    private Set<Advert> advertSet;
}
