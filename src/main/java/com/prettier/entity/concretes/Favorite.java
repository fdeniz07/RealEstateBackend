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
@Table(name = "favorites")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class Favorite extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore  //coklu iliskilerde tablonun birinde bu annotation kullanilir, aksi durumda sout yapildiginda sonsuz döngüye girer!
    private User user;

    @ManyToOne
    @JoinColumn(name = "advert_id")
    @JsonIgnore  //coklu iliskilerde tablonun birinde bu annotation kullanilir, aksi durumda sout yapildiginda sonsuz döngüye girer!
    private Advert advert;



}
