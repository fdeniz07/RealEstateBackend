package com.prettier.service.concretes;

import com.prettier.entity.concretes.City;
import com.prettier.payloads.mapper.CityMapper;
import com.prettier.payloads.mapper.CountryMapper;
import com.prettier.payloads.request.concretes.CityRequest;
import com.prettier.payloads.request.concretes.CityUpdateRequest;
import com.prettier.payloads.response.concretes.CityResponse;
import com.prettier.repository.CityRepository;
import com.prettier.service.abstracts.CityService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.cities.CityAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.cities.CityAlreadyExistsException;
import com.prettier.shared.exception.exceptions.cities.CityNotCreatedException;
import com.prettier.shared.exception.exceptions.cities.CityNotFoundException;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class CityManager implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private CountryMapper countryMapper;

    //NOT: *********** Data Inilitalizer icin gerekli metotlar *************************

    @Override
    public List<City> getAllCities() {

        return cityRepository.findAll();
    }

    @Override
    public City getById(Integer id) {

        return cityRepository.findById(id);
    }


    //NOT: *********** City Manager standart metotlar *************************************

    //Not: getAll() *********************************************************************************************************************************

    @Override
    public Page<CityResponse> getCities(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getCities]", this.getClass().getSimpleName());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        List<CityResponse> cities = getAllCities()
                .stream()
                .map(cityMapper::toResponse)
                .toList();

        if (cities.isEmpty()) {
            throw new CityNotFoundException(language, FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION, "Cities not found");
        }
        log.debug("[{}][getCities] -> response: {}", this.getClass().getSimpleName(), cities);
        return cityRepository.findAll(pageable).map(cityMapper::toResponse);
    }


    //Not: getByIdResponse() ****************************************************************************************************************************
    @Override
    public CityResponse getByCityIdResponse(Language language, Long id) {

        log.debug("[{}][getCity] -> request cityId: {}", this.getClass().getSimpleName(), id);

        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(language, FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION, "City not found for city id: " + id));

        log.debug("[{}][getCity] -> response: {}", this.getClass().getSimpleName(), city);
        return cityMapper.toResponse(city);
    }

    //Not: add() ****************************************************************************************************************************************
    @Override
    public CityResponse add(Language language, CityRequest cityRequest) {

        log.debug("[{}][createCity] -> request: {}", this.getClass().getSimpleName(), cityRequest);

        // City adi veritabaninda mevcut mu kontrolü
        boolean existsByCityName = existsByCityName(language, cityRequest.getName());

        //City db de mevcutsa hata firlat, yoksa kaydet
        if (existsByCityName) {
            throw new CityNotCreatedException(language, FriendlyMessageCodes.CITY_NOT_CREATED_EXCEPTION, "city request: " + cityRequest.toString());
        } else {
            City newCity = cityMapper.toCity(cityRequest);
            City response = cityRepository.save(newCity);
            log.debug("[{}][createCity] -> response: {}", this.getClass().getSimpleName(), response);
            return cityMapper.toResponse(response);
        }
    }

    //Not: update() *********************************************************************************************************************************
    @Override
    public CityResponse update(Language language, CityUpdateRequest cityUpdateRequest, Long id) {

        log.debug("[{}][updateCity] -> request: {} {}", this.getClass().getSimpleName(), id, cityUpdateRequest);

        //City gercekte db de var mi kontrolü
        City existingCity = getCity(language, id);

        //Güncelleme islemini yap
        cityMapper.toUpdatedCity(cityUpdateRequest, existingCity);

        // Veritabanına güncellenmiş City'yi kaydet
        City updatedCity = cityRepository.save(existingCity);

        log.debug("[{}][updateCity] -> response: {}", this.getClass().getSimpleName(), updatedCity);
        return cityMapper.toResponse(updatedCity);
    }

    //Not: update2() - manuel mapping ********************************************************************************************************************
//    @Override
//    public CityResponse update2(Language language, CityUpdateRequest cityUpdateRequest, Long id) {
//
//        log.debug("[{}][updateCity] -> request: {} {}", this.getClass().getSimpleName(), id, cityUpdateRequest);
//        //City Var mi kontrolü
//        City updatedCity = getCity(language, id);
//        //cityUpdateRequest.setId(id);
//        //City mevcutsa requestten geleni city'e cevir ve kaydet
//        // City updatedCity = cityMapper.toUpdatedCity(cityUpdateRequest, existingCity);
//        updatedCity.setName(cityUpdateRequest.getName());
//        updatedCity.setDeleted(cityUpdateRequest.isDeleted());
//        updatedCity.setCountry(updatedCity.getCountry());
//        updatedCity.setCreateAt(updatedCity.getCreateAt());
//        cityRepository.save(updatedCity);
//
//
//        CityResponse cityResponse = cityMapper.toResponse(updatedCity);
//        log.debug("[{}][updateCity] -> response: {}", this.getClass().getSimpleName(), cityResponse);
//        return cityResponse;
//    }

    //Not: delete() *********************************************************************************************************************************
    @Override
    public CityResponse softDelete(Language language, Long id) {

        //City Var mi kontrolü
        log.debug("[{}][deleteCity] -> request cityId: {}", this.getClass().getSimpleName(), id);
        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(language, FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION, "City not found for city id: " + id));

        //City var ama isDeleted=true mu kontrolü
        try {
            city = getCity(language, id);
            city.setDeleted(true);
            CityResponse cityResponse = cityMapper.toResponse(cityRepository.save(city));
            log.debug("[{}][deleteCity] -> response: {}", this.getClass().getSimpleName(), cityResponse);
            return cityResponse;
        } catch (CityNotFoundException cityNotFoundException) {
            throw new CityAlreadyDeletedException(language, FriendlyMessageCodes.CITY_ALREADY_DELETED, "City already deleted city id: " + id);
        }
    }

    //Not: Other *********************************************************************************************************************************

    //!!! Ilgili Id, City tablosunda var mi kontrolü
    public City getCity(Language language, Long cityId) {

        log.debug("[{}][getCity] -> request cityId: {}", this.getClass().getSimpleName(), cityId);
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(language, FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION, "City not found for city id: " + cityId));

        log.debug("[{}][getCity] -> response: {}", this.getClass().getSimpleName(), city);
        return city;
    }

    //!!! Ilgili CityName, City tablosunda var mi kontrolü
    public boolean existsByCityName(Language language, String cityName) {

        log.debug("[{}][existsByCityName] -> request cityName: {}", this.getClass().getSimpleName(), cityName);
        if (cityRepository.existsByName(cityName)) {
            throw new CityAlreadyExistsException(language, FriendlyMessageCodes.CITY_ALREADY_EXISTS, "This City already exists for city name: " + cityName);
        }

        log.debug("[{}][existsByCityName] -> response: {}", this.getClass().getSimpleName(), cityName);
        return false;
    }

    //Not: getById() for out Service ************************************************************************************************************
    @Override
    public City getByCityId(Language language, Long id) {

        log.debug("[{}][getCity] -> request cityId: {}", this.getClass().getSimpleName(), id);

        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(language, FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION, "City not found for city id: " + id));

        log.debug("[{}][getCity] -> response: {}", this.getClass().getSimpleName(), city);
        return city;
    }
}
