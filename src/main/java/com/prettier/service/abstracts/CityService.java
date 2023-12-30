package com.prettier.service.abstracts;

import com.prettier.entity.concretes.City;
import com.prettier.payload.request.concretes.CityRequest;
import com.prettier.payload.request.concretes.CityUpdateRequest;
import com.prettier.payload.response.concretes.CityResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface CityService extends Serializable {

    Page<CityResponse> getCities(Language language, int page, int size, String sort, String type);

    CityResponse getByIdCity(Language language, Long id);

    City add(Language language, CityRequest cityRequest);

    City update(Language language, CityUpdateRequest cityUpdateRequest, Long id);

    City delete(Language language, Long id);
}
