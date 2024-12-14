package com.prettier.service.abstracts;

import com.prettier.entity.concretes.Country;
import com.prettier.payloads.request.concretes.CountryRequest;
import com.prettier.payloads.request.concretes.CountryUpdateRequest;
import com.prettier.payloads.response.concretes.CountryResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface CountryService extends Serializable {

    //NOT: *********** Data Inilitalizer icin gerekli metotlar *************************

    List<Country> getAllCountries();

    Country getById(Integer id);


    //NOT: *********** Country Manager standart metotlar *************************************

    Page<CountryResponse> getCountries(Language language, int page, int size, String sort, String type);

    CountryResponse getByCountryId(Language language, Long id);

    Country add(Language language, CountryRequest countryRequest);

    CountryResponse update(Language language, CountryUpdateRequest countryUpdateRequest, Long id);

    CountryResponse softDelete(Language language, Long id);
}
