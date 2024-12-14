package com.prettier.controller;

import com.prettier.entity.concretes.Advert;
import com.prettier.payloads.mapper.AdvertMapper;
import com.prettier.payloads.request.concretes.AdvertRequest;
import com.prettier.payloads.request.concretes.AdvertUpdateRequest;
import com.prettier.shared.exception.globalExceptionHandling.FriendlyMessage;
import com.prettier.shared.exception.globalExceptionHandling.InternalApiResponse;
import com.prettier.payloads.response.concretes.AdvertResponse;
import com.prettier.service.abstracts.AdvertService;
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

@RestController
@RequiredArgsConstructor
@Tag(name = "Advert", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/adverts")
@Slf4j //Log eklemek icin kullaniyoruz
public class AdvertController {

    private final AdvertService advertService;
    private final AdvertMapper advertMapper;

    @GetMapping("/{language}/getAll")
    public Page<AdvertResponse> getAll(
            @PathVariable("language") Language language,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {

        return advertService.getAll(language, page, size, sort, type);
    }

//    @Operation(
//            summary = "Retrieve a Advert with Active by Id",
//            description = "Get a Advert object by specifying its id. The response is Advert object with id, title, description and published status.",
//            tags = {"advert", "get"})
    @GetMapping("/{language}/getListWithActive")
    public Page<AdvertResponse> getListWithActive(
            @PathVariable("language") Language language,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {

        return advertService.getListWithActive(language, page, size, sort, type);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/add")
    public InternalApiResponse<AdvertResponse> addAdvert(@PathVariable("language") Language language, @RequestBody @Valid AdvertRequest advertRequest) {

        log.debug("[{}][createAdvert] -> request: {}", this.getClass().getSimpleName(), advertRequest);
        Advert advert = advertService.add(language, advertRequest);

        AdvertResponse advertResponse = advertMapper.toResponse(advert);
        log.debug("[{}][createAdvert] -> response: {}", this.getClass().getSimpleName(), advertResponse);

        return InternalApiResponse.<AdvertResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.ADVERT_SUCCESSFULLY_CREATED))
                        .build()
                ).httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(advertResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{language}/update/{advertId}")
    public InternalApiResponse<AdvertResponse> updateAdvert(@PathVariable("language") Language language,
                                                            @PathVariable("advertId") Long id,
                                                            @RequestBody  @Valid AdvertUpdateRequest advertUpdateRequest) {

        log.debug("[{}][updateAdvert] -> request: {} {}", this.getClass().getSimpleName(), id, advertUpdateRequest);
        Advert advert = advertService.update(language, advertUpdateRequest, id);

        AdvertResponse advertResponse = advertMapper.toResponse(advert);
        log.debug("[{}][updateAdvert] -> response: {}", this.getClass().getSimpleName(), advertResponse);

        return InternalApiResponse.<AdvertResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.ADVERT_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(advertResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/delete/{advertId}")
    public InternalApiResponse<AdvertResponse> deleteAdvert(@PathVariable("language") Language language,
                                                            @PathVariable("advertId") Long id) {
        log.debug("[{}][deleteAdvert] -> request advertId: {}", this.getClass().getSimpleName(), id);
        Advert advert = advertService.delete(language, id);
        AdvertResponse advertResponse = advertMapper.toResponse(advert);
        log.debug("[{}][deleteAdvert] -> response: {}", this.getClass().getSimpleName(), advertResponse);
        return InternalApiResponse.<AdvertResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.ADVERT_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(advertResponse)
                .build();
    }
}
