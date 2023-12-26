package com.prettier.payload.request.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.User;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

public class FavoriteRequest extends BaseEntityRequest implements Serializable {

    private User user;
    private Advert advert;

}
