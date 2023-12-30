package com.prettier.service.concretes;

import com.prettier.entity.concretes.City;
import com.prettier.payload.mapper.CityMapper;
import com.prettier.payload.request.concretes.CityRequest;
import com.prettier.payload.request.concretes.CityUpdateRequest;
import com.prettier.payload.response.concretes.CityResponse;
import com.prettier.repository.CityRepository;
import com.prettier.service.abstracts.CityService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
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

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityManager implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public Page<CityResponse> getCities(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getCities]", this.getClass().getSimpleName());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        List<CityResponse> cities = cityRepository.findAll()
                .stream()
                .map(cityMapper::toResponse)
                .collect(Collectors.toList());

        if (cities.isEmpty()) {
            throw new CityNotFoundException(language, FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION, "Cities not found");
        }
        log.debug("[{}][getCities] -> response: {}", this.getClass().getSimpleName(), cities);
        return cityRepository.findAll(pageable).map(cityMapper::toResponse);
    }

    @Override
    public CityResponse getByIdCity(Language language, Long id) {
        log.debug("[{}][getCity] -> request cityId: {}", this.getClass().getSimpleName(), id);

        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException(language, FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION, "City not found for city id: " + id));

        log.debug("[{}][getCity] -> response: {}", this.getClass().getSimpleName(), city);
        return cityMapper.toResponse(city);
    }

    @Override
    public City add(Language language, CityRequest cityRequest) {
        log.debug("[{}][createCity] -> request: {}", this.getClass().getSimpleName(), cityRequest);
        try {
            City city = cityMapper.toCity(cityRequest);
            CityResponse response = cityMapper.toResponse(cityRepository.save(city));
            log.debug("[{}][createCity] -> response: {}", this.getClass().getSimpleName(), response);
            return city;
        } catch (Exception exception) {
            throw new CityNotCreatedException(language, FriendlyMessageCodes.CITY_NOT_CREATED_EXCEPTION, "city request: " + cityRequest.toString());
        }
    }

    @Override
    public City update(Language language, CityUpdateRequest cityUpdateRequest, Long id) {
        log.debug("[{}][updateCity] -> request: {} {}", this.getClass().getSimpleName(), id, cityUpdateRequest);
        City city = getCity(language,id);
        city.setName(cityUpdateRequest.getName());
        City cityResponse = cityRepository.save(city);
        log.debug("[{}][updateCity] -> response: {}", this.getClass().getSimpleName(), cityResponse);
        return cityResponse;
    }

    @Override
    public City delete(Language language, Long id) {
        return null;
    }


    public City getCity(Language language, Long cityId) {
        log.debug("[{}][getCity] -> request cityId: {}", this.getClass().getSimpleName(), cityId);
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(language, FriendlyMessageCodes.CITY_NOT_FOUND_EXCEPTION, "City not found for city id: " + cityId));

        log.debug("[{}][getCity] -> response: {}", this.getClass().getSimpleName(), city);
        return city;
    }
}
