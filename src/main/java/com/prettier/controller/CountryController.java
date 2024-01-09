package com.prettier.controller;

import com.prettier.entity.concretes.Country;
import com.prettier.payload.mapper.CountryMapper;
import com.prettier.payload.request.concretes.CountryRequest;
import com.prettier.payload.request.concretes.CountryUpdateRequest;
import com.prettier.payload.response.FriendlyMessage;
import com.prettier.payload.response.InternalApiResponse;
import com.prettier.payload.response.concretes.CountryResponse;
import com.prettier.service.concretes.CountryManager;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Country", description = "Prettier Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/countries")
@Slf4j
public class CountryController {

    private final CountryManager countryService;
    private final CountryMapper countryMapper;


    //Not: getAll() *********************************************************************************************************************************
    @GetMapping(value = "/{language}/getAll") //http://localhost:8080/countries/getAll
    public Page<CountryResponse> getCountries(
            @PathVariable("language") Language language,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "50") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        return countryService.getCountries(language, page, size, sort, type);
    }

    //Not: getById() *********************************************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{countryId}")
    public InternalApiResponse<CountryResponse> getCountry(@PathVariable("language") Language language,
                                                           @PathVariable("countryId") Long id) {
        log.debug("[{}][getCountry] -> request countryId: {}", this.getClass().getSimpleName(), id);
        CountryResponse countryResponse = countryService.getByCountryId(language, id);

        log.debug("[{}][getCountry] -> response: {}", this.getClass().getSimpleName(), countryResponse);
        return InternalApiResponse.<CountryResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(countryResponse)
                .build();
    }

    //Not: add() ****************************************************************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/add")
    public InternalApiResponse<CountryResponse> addCountry(@PathVariable("language") Language language,
                                                           @RequestBody CountryRequest countryRequest) {

        log.debug("[{}][createCountry] -> request: {}", this.getClass().getSimpleName(), countryRequest);
        Country country = countryService.add(language, countryRequest);

        CountryResponse countryResponse = countryMapper.toResponse(country);
        log.debug("[{}][createCountry] -> response: {}", this.getClass().getSimpleName(), countryResponse);
        return InternalApiResponse.<CountryResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.COUNTRY_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(countryResponse)
                .build();
    }


    //Not: update() *********************************************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{countryId}")
    public InternalApiResponse<CountryResponse> update(@PathVariable("language") Language language,
                                                       @PathVariable("countryId") Long id,
                                                       @RequestBody CountryUpdateRequest countryUpdateRequest) {

        log.debug("[{}][updateCountry] -> request: {} {}", this.getClass().getSimpleName(), id, countryUpdateRequest);
        CountryResponse countryResponse = countryService.update(language, countryUpdateRequest, id);

        log.debug("[{}][updateCountry] -> response: {}", this.getClass().getSimpleName(), countryResponse);

        return InternalApiResponse.<CountryResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.COUNTRY_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(countryResponse)
                .build();
    }

    //Not: delete() *********************************************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/delete/{countryId}")
    public InternalApiResponse<CountryResponse> deleteCountry(@PathVariable("language") Language language,
                                                              @PathVariable("countryId") Long id) {
        log.debug("[{}][deleteCountry] -> request countryId: {}", this.getClass().getSimpleName(), id);
        CountryResponse countryResponse = countryService.softDelete(language, id);

        log.debug("[{}][deleteCountry] -> response: {}", this.getClass().getSimpleName(), countryResponse);
        return InternalApiResponse.<CountryResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.COUNTRY_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(countryResponse)
                .build();
    }
}
