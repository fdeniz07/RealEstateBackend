package com.prettier.service.abstracts;

import com.prettier.entity.concretes.Advert;
import com.prettier.payload.request.concretes.AdvertRequest;
import com.prettier.payload.response.FriendlyResponseMessage;
import com.prettier.payload.response.concretes.AdvertResponse;
import org.springframework.data.domain.Page;

public interface IAdvertService {

    Page<AdvertResponse> getAll(int page, int size, String sort, String type);

    Page<AdvertResponse> getListWithActive(int page, int size, String sort, String type);

    FriendlyResponseMessage<AdvertResponse> getByIdAllType(Long id);

    FriendlyResponseMessage<AdvertResponse> getByIdActive(Long id);

    FriendlyResponseMessage<AdvertResponse> add(AdvertRequest advertRequest);

    FriendlyResponseMessage<AdvertResponse> update(AdvertRequest advertRequest, Advert existAdvert,Long id);

    FriendlyResponseMessage<AdvertResponse> delete(AdvertRequest advertRequest, Long Id);

}
