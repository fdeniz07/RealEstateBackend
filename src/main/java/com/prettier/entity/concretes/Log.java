package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.abstracts.BaseEntity;
import com.prettier.entity.enums.LogAction;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "logs")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString

public class Log extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private LogAction logAction;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore //coklu iliskilerde tablonun birinde bu annotation kullanilir, aksi durumda sout yapildiginda sonsuz döngüye girer!
    private User user;

    @ManyToOne
    @JoinColumn(name = "advert_id", nullable = false)
    @JsonIgnore  //coklu iliskilerde tablonun birinde bu annotation kullanilir, aksi durumda sout yapildiginda sonsuz döngüye girer!
    private Advert advert;


    //Todo ilişkiler sonrası eklenecek


}
