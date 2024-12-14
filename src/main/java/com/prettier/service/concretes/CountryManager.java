package com.prettier.service.concretes;

import com.prettier.entity.concretes.Country;
import com.prettier.payloads.mapper.CountryMapper;
import com.prettier.payloads.request.concretes.CountryRequest;
import com.prettier.payloads.request.concretes.CountryUpdateRequest;
import com.prettier.payloads.response.concretes.CountryResponse;
import com.prettier.repository.CountryRepository;
import com.prettier.service.abstracts.CountryService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.countries.CountryAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.countries.CountryAlreadyExistsException;
import com.prettier.shared.exception.exceptions.countries.CountryNotCreatedException;
import com.prettier.shared.exception.exceptions.countries.CountryNotFoundException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CountryManager implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    //NOT: *********** Data Inilitalizer icin gerekli metotlar *************************

    @Override
    public List<Country> getAllCountries() {

        return countryRepository.findAll();
    }

    @Override
    public Country getById(Integer id) {

        return countryRepository.findById(id);
    }


    //NOT: *********** City Manager standart metotlar *************************************

    //Not: getAll() *********************************************************************************************************************************
    @Override
    public Page<CountryResponse> getCountries(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getCities]", this.getClass().getSimpleName());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        List<CountryResponse> countries = countryRepository.findAll()
                .stream()
                .map(countryMapper::toResponse)
                .collect(Collectors.toList());

        if (countries.isEmpty()) {
            throw new CountryNotFoundException(language, FriendlyMessageCodes.COUNTRY_NOT_FOUND_EXCEPTION, "Cities not found");
        }
        log.debug("[{}][getCities] -> response: {}", this.getClass().getSimpleName(), countries);
        return countryRepository.findAll(pageable).map(countryMapper::toResponse);
    }

    //Not: getById() *********************************************************************************************************************************
    @Override
    public CountryResponse getByCountryId(Language language, Long id) {

        log.debug("[{}][getCountry] -> request countryId: {}", this.getClass().getSimpleName(), id);

        Country country = countryRepository.findById(id).orElseThrow(() -> new CountryNotFoundException(language, FriendlyMessageCodes.COUNTRY_NOT_FOUND_EXCEPTION, "Country not found for country id: " + id));

        log.debug("[{}][getCountry] -> response: {}", this.getClass().getSimpleName(), country);
        return countryMapper.toResponse(country);
    }

    //Not: add() ****************************************************************************************************************************************
    @Override
    public Country add(Language language, CountryRequest countryRequest) {

        log.debug("[{}][createCountry] -> request: {}", this.getClass().getSimpleName(), countryRequest);

        boolean existsByCountryName = existsByCountryName(language, countryRequest.getName());

        if (existsByCountryName) {
            throw new CountryNotCreatedException(language, FriendlyMessageCodes.COUNTRY_NOT_CREATED_EXCEPTION, "country request: " + countryRequest.toString());
        } else {
            Country newCountry = countryMapper.toCountry(countryRequest);
            Country response = countryRepository.save(newCountry);
            log.debug("[{}][createCountry] -> response: {}", this.getClass().getSimpleName(), response);
            return response;
        }

//        try {
//            newCountry = countryMapper.toCountry(countryRequest);
//            Country response = countryRepository.save(newCountry);
//            log.debug("[{}][createCountry] -> response: {}", this.getClass().getSimpleName(), response);
//            return response;
//        } catch (Exception exception) {
//            throw new CountryNotCreatedException(language, FriendlyMessageCodes.COUNTRY_NOT_CREATED_EXCEPTION, "country request: " + countryRequest.toString());
//        }
    }

    //Not: update() *********************************************************************************************************************************
    @Override
    public CountryResponse update(Language language, CountryUpdateRequest countryUpdateRequest, Long id) {

        log.debug("[{}][updateCountry] -> request: {} {}", this.getClass().getSimpleName(), id, countryUpdateRequest);

        //Country Var mi kontrolü
        Country existingCountry = getCountry(language, id);

        //Country mevcutsa requestten geleni country'e cevir ve kaydet
        // Country newCountry = countryMapper.toUpdatedCountry(countryUpdateRequest, existingCountry);
//        newCountry.setName(countryUpdateRequest.getName());
//        newCountry.setDeleted(countryUpdateRequest.isDeleted());
//        newCountry.setCreateAt(updatedCountry.getCreateAt());
//        countryRepository.save(newCountry);
        // CountryResponse countryResponse = countryMapper.toResponse(updatedCountry);

        CountryResponse updatedCountry = countryMapper.toResponse(countryRepository.save(existingCountry));

        log.debug("[{}][updateCountry] -> response: {}", this.getClass().getSimpleName(), updatedCountry);
        return updatedCountry;
    }

    //Not: delete() *********************************************************************************************************************************
    @Override
    public CountryResponse softDelete(Language language, Long id) {

        //Country Var mi kontrolü
        log.debug("[{}][deleteCountry] -> request countryId: {}", this.getClass().getSimpleName(), id);
        Country country = countryRepository.findById(id).orElseThrow(() -> new CountryNotFoundException(language, FriendlyMessageCodes.COUNTRY_NOT_FOUND_EXCEPTION, "Country not found for country id: " + id));

        //Country var ama isDeleted=true mu kontrolü
        try {
            country = getCountry(language, id);
            country.setDeleted(true);
            CountryResponse countryResponse = countryMapper.toResponse(countryRepository.save(country));
            log.debug("[{}][deleteCountry] -> response: {}", this.getClass().getSimpleName(), countryResponse);
            return countryResponse;
        } catch (CountryNotFoundException countryNotFoundException) {
            throw new CountryAlreadyDeletedException(language, FriendlyMessageCodes.COUNTRY_ALREADY_DELETED, "Country already deleted country id: " + id);
        }
    }

    //Not: Other *********************************************************************************************************************************

    //!!! Ilgili Id, Country tablosunda var mi kontrolü
    public Country getCountry(Language language, Long countryId) {

        log.debug("[{}][getCountry] -> request countryId: {}", this.getClass().getSimpleName(), countryId);
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(language, FriendlyMessageCodes.COUNTRY_NOT_FOUND_EXCEPTION, "Country not found for country id: " + countryId));

        log.debug("[{}][getCountry] -> response: {}", this.getClass().getSimpleName(), country);
        return country;
    }

    //!!! Ilgili CountryName, Country tablosunda var mi kontrolü
    public boolean existsByCountryName(Language language, String countryName) {

        log.debug("[{}][getCountry] -> request countryName: {}", this.getClass().getSimpleName(), countryName);
        if (countryRepository.existsByName(countryName)) {
            throw new CountryAlreadyExistsException(language, FriendlyMessageCodes.COUNTRY_ALREADY_EXISTS, "This Country already exists for country name: " + countryName);
        }

        //.orElseThrow(() -> new CountryAlreadyExistsException(language, FriendlyMessageCodes.COUNTRY_ALREADY_EXISTS, "This Country already exists for country name: " + countryName));

        log.debug("[{}][getCountry] -> response: {}", this.getClass().getSimpleName(), countryName);
        return false;
    }
}