package com.prettier.payloads.request.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.User;
import com.prettier.payloads.request.abstracts.BaseEntityRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class FavoriteRequest extends BaseEntityRequest{

    private User user;
    private Advert advert;

}
