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

/**
 * Service implementation class that manages City-related operations.
 * This class handles CRUD operations and pagination for Cities, implementing
 * the CityService interface to provide city management functionality.
 * Includes methods for both internal service operations and data initialization.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CityManager implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private CountryMapper countryMapper;

    //NOT: *********** Data Inilitalizer icin gerekli metotlar *************************
    // Data Initializer Methods

    /**
     * Retrieves all cities from the database.
     * Used primarily for data initialization purposes.
     *
     * @return List of all City entities in the database
     */
    @Override
    public List<City> getAllCities() {

        return cityRepository.findAll();
    }


    /**
     * Retrieves a city by its ID.
     * Used primarily for data initialization purposes.
     *
     * @param id The ID of the city to retrieve
     * @return The City entity with the specified ID
     */
    @Override
    public City getById(Integer id) {

        return cityRepository.findById(id);
    }


    //NOT: *********** City Manager standart metotlar *************************************

    //Not: getAll() *********************************************************************************************************************************
    /**
     * Retrieves a paginated list of all cities.
     *
     * @param language The language for error messages and localization
     * @param page The page number for pagination
     * @param size The number of items per page
     * @param sort The field to sort by
     * @param type The sort direction ("asc" or "desc")
     * @return A Page of CityResponse containing all cities
     * @throws CityNotFoundException if no cities are found
     */
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
    /**
     * Retrieves a city by ID and returns it as a response object.
     *
     * @param language The language for error messages and localization
     * @param id The ID of the city to retrieve
     * @return CityResponse containing the city details
     * @throws CityNotFoundException if the city is not found
     */
    @Override
    public CityResponse getByCityIdResponse(Language language, Long id) {

        log.debug("[{}][getCity] -> request cityId: {}", this.getClass().getSimpleName(), id);

        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(language, FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION, "City not found for city id: " + id));

        log.debug("[{}][getCity] -> response: {}", this.getClass().getSimpleName(), city);
        return cityMapper.toResponse(city);
    }

    //Not: add() ****************************************************************************************************************************************
    /**
     * Creates a new city.
     *
     * @param language The language for error messages and localization
     * @param cityRequest The city creation request containing city details
     * @return CityResponse containing the created city details
     * @throws CityNotCreatedException if the city cannot be created
     * @throws CityAlreadyExistsException if a city with the same name already exists
     */
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
    /**
     * Updates an existing city.
     *
     * @param language The language for error messages and localization
     * @param cityUpdateRequest The city update request containing updated details
     * @param id The ID of the city to update
     * @return CityResponse containing the updated city details
     * @throws CityNotFoundException if the city is not found
     */
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
    /**
     * Performs a soft delete on a city by marking it as deleted.
     *
     * @param language The language for error messages and localization
     * @param id The ID of the city to delete
     * @return CityResponse containing the deleted city details
     * @throws CityNotFoundException if the city is not found
     * @throws CityAlreadyDeletedException if the city is already deleted
     */
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
    // Helper Methods

    /**
     * Helper method to retrieve a city entity by ID.
     *
     * @param language The language for error messages and localization
     * @param cityId The ID of the city to retrieve
     * @return City entity
     * @throws CityNotFoundException if the city is not found
     */
    //!!! Ilgili Id, City tablosunda var mi kontrolü
    public City getCity(Language language, Long cityId) {

        log.debug("[{}][getCity] -> request cityId: {}", this.getClass().getSimpleName(), cityId);
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(language, FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION, "City not found for city id: " + cityId));

        log.debug("[{}][getCity] -> response: {}", this.getClass().getSimpleName(), city);
        return city;
    }


    /**
     * Helper method to check if a city with the given name exists.
     *
     * @param language The language for error messages and localization
     * @param cityName The name to check
     * @return boolean indicating whether the city exists
     * @throws CityAlreadyExistsException if a city with the given name exists
     */
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
    /**
     * Retrieves a city entity by ID for external service use.
     *
     * @param language The language for error messages and localization
     * @param id The ID of the city to retrieve
     * @return City entity
     * @throws CityNotFoundException if the city is not found
     */
    @Override
    public City getByCityId(Language language, Long id) {

        log.debug("[{}][getCity] -> request cityId: {}", this.getClass().getSimpleName(), id);

        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(language, FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION, "City not found for city id: " + id));

        log.debug("[{}][getCity] -> response: {}", this.getClass().getSimpleName(), city);
        return city;
    }
}
