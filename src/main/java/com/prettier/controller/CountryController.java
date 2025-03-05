package com.prettier.controller;

import com.prettier.entity.concretes.Country;
import com.prettier.payloads.mapper.CountryMapper;
import com.prettier.payloads.request.concretes.CountryRequest;
import com.prettier.payloads.request.concretes.CountryUpdateRequest;
import com.prettier.shared.exception.globalExceptionHandling.FriendlyMessage;
import com.prettier.shared.exception.globalExceptionHandling.InternalApiResponse;
import com.prettier.payloads.response.concretes.CountryResponse;
import com.prettier.service.concretes.CountryManager;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing country operations in the system.
 * Handles endpoints for creating, retrieving, updating, and deleting countries.
 *
 * <p>This controller provides functionality for real estate country data
 * with support for different languages and pagination.</p>
 *
 * @author Fatih Deniz
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Country", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/countries")
@Slf4j
public class CountryController {

    private final CountryManager countryService;
    private final CountryMapper countryMapper;


    //Not: getAll() *********************************************************************************************************************************
    /**
     * Retrieves all countries with pagination support.
     *
     * @param language The language for the response content
     * @param page The page number (zero-based) to retrieve
     * @param size The size of the page to retrieve
     * @param sort The field to sort by
     * @param type The sort direction (asc or desc)
     * @return An internal API response containing a page of country responses
     */
    @GetMapping(value = "/{language}/getAll") //http://localhost:8080/countries/getAll
    public InternalApiResponse<Page<CountryResponse>> getCountries(@PathVariable("language") Language language,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "50") int size,
                                                                   @RequestParam(value = "sort", defaultValue = "name") String sort,
                                                                   @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        log.debug("[{}][getCountries]", this.getClass().getSimpleName());
        Page<CountryResponse> countryResponses = countryService.getCountries(language, page, size, sort, type);

        log.debug("[{}][getCountries] -> response: {}", this.getClass().getSimpleName(), countryResponses);
        return InternalApiResponse.<Page<CountryResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(countryResponses)
                .build();
    }

    //Not: getById() *********************************************************************************************************************************
    /**
     * Retrieves a specific country by its ID.
     *
     * @param language The language for the response content
     * @param id The ID of the country to retrieve
     * @return An internal API response containing the country response
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{countryId}")
    public InternalApiResponse<CountryResponse> getCountry(@PathVariable("language") Language language,
                                                           @PathVariable("countryId") Long id
    ) {
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
    /**
     * Creates a new country in the system.
     *
     * @param language The language for the response content
     * @param countryRequest The country data to create
     * @return An internal API response containing the created country
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/add")
    public InternalApiResponse<CountryResponse> addCountry(@PathVariable("language") Language language,
                                                           @RequestBody @Valid CountryRequest countryRequest
    ) {
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
    /**
     * Updates an existing country in the system.
     *
     * @param language The language for the response content
     * @param id The ID of the country to update
     * @param countryUpdateRequest The updated country data
     * @return An internal API response containing the updated country
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{countryId}")
    public InternalApiResponse<CountryResponse> update(@PathVariable("language") Language language,
                                                       @PathVariable("countryId") Long id,
                                                       @RequestBody @Valid CountryUpdateRequest countryUpdateRequest
    ) {
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
    /**
     * Soft deletes a country from the system.
     *
     * @param language The language for the response content
     * @param id The ID of the country to delete
     * @return An internal API response containing the deleted country
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/delete/{countryId}")
    public InternalApiResponse<CountryResponse> deleteCountry(@PathVariable("language") Language language,
                                                              @PathVariable("countryId") Long id
    ) {
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
