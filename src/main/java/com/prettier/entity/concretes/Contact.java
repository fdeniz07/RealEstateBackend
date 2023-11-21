package com.prettier.entity.concretes;

import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class Contact extends BaseEntity {

    @Column(name="first_name",nullable = false)
    @Size(max = 30)
    private String firstName;
    @Column(name="last_name",nullable = false)
    @Size(max = 30)
    private String lastName;
    @Column(nullable = false)
    @Size(max = 30)
    private String email;
    @Column(nullable = false)
    @Size(max = 30)
    private String message;


}
