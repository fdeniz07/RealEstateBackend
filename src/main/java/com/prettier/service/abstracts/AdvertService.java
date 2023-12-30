package com.prettier.service.abstracts;

import com.prettier.entity.concretes.Advert;
import com.prettier.payload.request.concretes.AdvertRequest;
import com.prettier.payload.request.concretes.AdvertUpdateRequest;
import com.prettier.payload.response.concretes.AdvertResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface AdvertService extends Serializable {

    Page<AdvertResponse> getAll(Language language, int page, int size, String sort, String type);

    Page<AdvertResponse> getListWithActive(Language language, int page, int size, String sort, String type);

    AdvertResponse getByIdAllType(Language language, Long id);

    AdvertResponse getByIdActive(Language language, Long id);

    Advert add(Language language, AdvertRequest advertRequest);

    Advert update(Language language, AdvertUpdateRequest advertUpdateRequest, Long id);

    Advert delete(Language language, Long id);

}
