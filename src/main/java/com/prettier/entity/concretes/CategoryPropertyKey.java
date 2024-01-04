package com.prettier.entity.concretes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "category_property_keys")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPropertyKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 80)
    private String name;

    @Column(name = "built_in", nullable = false)
    private boolean builtIn;

    // RELATIONS---------------------------------------------------------------------
//Relations with Parent Category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

//    //Relations With Child 'category_property_values'
    @OneToMany(mappedBy = "categoryPropertyKey")
    private Set<CategoryPropertyValue> categoryPropertyValueSet;



}