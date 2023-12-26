package com.prettier.payload.response.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.User;
import com.prettier.payload.response.abstracts.BaseEntityResponse;

import java.io.Serializable;

public class FavoriteResponse extends BaseEntityResponse implements Serializable {

    private User user;
    private Advert advert;
}
