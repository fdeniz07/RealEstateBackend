package com.prettier.service.abstracts;

import com.prettier.entity.concretes.City;
import com.prettier.payloads.request.concretes.CityRequest;
import com.prettier.payloads.request.concretes.CityUpdateRequest;
import com.prettier.payloads.response.concretes.CityResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface CityService extends Serializable {

    //NOT: *********** Data Inilitalizer icin gerekli metotlar *************************

    List<City> getAllCities();

    City getById(Integer id);

    //NOT: *********** City Manager standart metotlar *************************************

    Page<CityResponse> getCities(Language language, int page, int size, String sort, String type);

    CityResponse getByCityIdResponse(Language language, Long id);
    City getByCityId(Language language, Long id);

    CityResponse add(Language language, CityRequest cityRequest);

    CityResponse update(Language language, CityUpdateRequest cityUpdateRequest, Long id);
//  CityResponse update2(Language language,CityUpdateRequest cityUpdateRequest, Long id);

    CityResponse softDelete(Language language, Long id);
}
