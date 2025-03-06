package com.prettier.controller;

import com.prettier.payloads.mapper.DistrictMapper;
import com.prettier.payloads.request.concretes.DistrictRequest;
import com.prettier.payloads.request.concretes.DistrictUpdateRequest;
import com.prettier.shared.exception.globalExceptionHandling.FriendlyMessage;
import com.prettier.shared.exception.globalExceptionHandling.InternalApiResponse;
import com.prettier.payloads.response.concretes.DistrictResponse;
import com.prettier.service.concretes.DistrictManager;
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
 * REST controller for managing district operations in the system.
 * Handles endpoints for creating, retrieving, updating, and deleting districts.
 *
 * <p>This controller provides functionality for real estate district data
 * with support for different languages and pagination.</p>
 *
 * @author Fatih Deniz
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "District", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/districts")
@Slf4j
public class DistrictController {

    private final DistrictManager districtService;
    private final DistrictMapper districtMapper;

    //Not: getAll() *********************************************************************************************************************************
    /**
     * Retrieves all districts with pagination support.
     *
     * @param language The language for the response content
     * @param page The page number (zero-based) to retrieve
     * @param size The size of the page to retrieve
     * @param sort The field to sort by
     * @param type The sort direction (asc or desc)
     * @return An internal API response containing a page of district responses
     */
    @GetMapping(value = "/{language}/districts") // http://localhost:8080/districts/EN/districts
    public InternalApiResponse<Page<DistrictResponse>> getDistricts(@PathVariable("language") Language language,
                                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "41") int size,
                                                                    @RequestParam(value = "sort", defaultValue = "name") String sort,
                                                                    @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        log.debug("[{}][getDistricts]", this.getClass().getSimpleName());
        Page<DistrictResponse> districtResponses = districtService.getDistricts(language, page, size, sort, type);

        log.debug("[{}][getDistricts] -> response: {}", this.getClass().getSimpleName(), districtResponses);
        return InternalApiResponse.<Page<DistrictResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(districtResponses)
                .build();
    }

    //Not: getById() *********************************************************************************************************************************
    /**
     * Retrieves a specific district by its ID.
     *
     * @param language The language for the response content
     * @param id The ID of the district to retrieve
     * @return An internal API response containing the district response
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{districtId}")
    public InternalApiResponse<DistrictResponse> getDistrict(@PathVariable("language") Language language,
                                                             @PathVariable("districtId") Long id
    ) {
        log.debug("[{}][getDistrict] -> request districtId: {}", this.getClass().getSimpleName(), id);
        DistrictResponse districtResponse = districtService.getByDistrictId(language, id);

        log.debug("[{}][getDistrict] -> response: {}", this.getClass().getSimpleName(), districtResponse);
        return InternalApiResponse.<DistrictResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(districtResponse)
                .build();
    }

    //Not: add() ****************************************************************************************************************************************
    /**
     * Creates a new district in the system.
     *
     * @param language The language for the response content
     * @param districtRequest The district data to create
     * @return An internal API response containing the created district
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/add")
    public InternalApiResponse<DistrictResponse> addDistrict(@PathVariable("language") Language language,
                                                             @RequestBody @Valid DistrictRequest districtRequest
    ) {
        log.debug("[{}][createDistrict] -> request: {}", this.getClass().getSimpleName(), districtRequest);
        DistrictResponse districtResponse = districtService.add(language, districtRequest);

        log.debug("[{}][createDistrict] -> response: {}", this.getClass().getSimpleName(), districtResponse);

        return InternalApiResponse.<DistrictResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.DISTRICT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(districtResponse)
                .build();
    }

    //Not: update() *********************************************************************************************************************************
    /**
     * Updates an existing district in the system.
     *
     * @param language The language for the response content
     * @param id The ID of the district to update
     * @param districtUpdateRequest The updated district data
     * @return An internal API response containing the updated district
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{districtId}")
    public InternalApiResponse<DistrictResponse> updateDistrict(@PathVariable("language") Language language,
                                                                @PathVariable("districtId") Long id,
                                                                @RequestBody @Valid  DistrictUpdateRequest districtUpdateRequest
    ) {
        log.debug("[{}][updateDistrict] -> request: {} {}", this.getClass().getSimpleName(), id, districtUpdateRequest);
        DistrictResponse districtResponse = districtService.update(language, districtUpdateRequest, id);

        log.debug("[{}][updateDistrict] -> response: {}", this.getClass().getSimpleName(), districtResponse);

        return InternalApiResponse.<DistrictResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.DISTRICT_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(districtResponse)
                .build();
    }

    //Not: delete() *********************************************************************************************************************************
    /**
     * Soft deletes a district from the system.
     *
     * @param language The language for the response content
     * @param id The ID of the district to delete
     * @return An internal API response containing the deleted district
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/delete/{districtId}")
    public InternalApiResponse<DistrictResponse> deleteDistrict(@PathVariable("language") Language language,
                                                                @PathVariable("districtId") Long id
    ) {
        log.debug("[{}][deleteDistrict] -> request districtId: {}", this.getClass().getSimpleName(), id);
        DistrictResponse districtResponse = districtService.softDelete(language, id);

        log.debug("[{}][deleteDistrict] -> response: {}", this.getClass().getSimpleName(), districtResponse);
        return InternalApiResponse.<DistrictResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.DISTRICT_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(districtResponse)
                .build();
    }
}
