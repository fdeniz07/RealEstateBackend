package com.prettier.payload.request.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.User;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class FavoriteRequest extends BaseEntityRequest{

    private User user;
    private Advert advert;

}
