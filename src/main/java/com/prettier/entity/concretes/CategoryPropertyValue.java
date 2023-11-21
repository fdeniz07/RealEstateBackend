package com.prettier.entity.concretes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "category_property_value")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryPropertyValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value", nullable = false)
    @Size(max = 100)
    private String value;

    //RELATION -----------------------------------------------------------------
//Realtion with Parent "category_property-keys"
    @ManyToOne
    @JoinColumn(name = "category_property_key_id")
    private CategoryPropertyKey categoryPropertyKey;

    // Relation with Parent Adverts table
    @ManyToOne
    @JoinColumn(name = "advert_id")
    private Advert advert;


}
