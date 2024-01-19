package com.prettier.controller;

import com.prettier.entity.concretes.Contact;
import com.prettier.payload.mapper.ContactMapper;
import com.prettier.payload.request.concretes.ContactRequest;
import com.prettier.payload.response.FriendlyMessage;
import com.prettier.payload.response.InternalApiResponse;
import com.prettier.payload.response.concretes.ContactResponse;
import com.prettier.payload.response.concretes.DistrictResponse;
import com.prettier.service.abstracts.ContactService;
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
@Tag(name = "Contact Message", description = "Prettier Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/contact-messages")
@Slf4j
public class ContactController {

    private final ContactService contactService;
    private final ContactMapper contactMapper;

    //Not: getAll() *********************************************************************************************************************************
    @GetMapping(value = "/{language}/contacts") // http://localhost:8080/contact-messages/EN/getAll
    public InternalApiResponse<Page<ContactResponse>> getContactMessages(@PathVariable("language") Language language,
                                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                                         @RequestParam(value = "size", defaultValue = "20") int size,
                                                                         @RequestParam(value = "sort", defaultValue = "email") String sort,
                                                                         @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        log.debug("[{}][getContactMessages]", this.getClass().getSimpleName());
        Page<ContactResponse> contactResponses = contactService.getContacts(language, page, size, sort, type);

        log.debug("[{}][getContactMessages] -> response: {}", this.getClass().getSimpleName(), contactResponses);
        return InternalApiResponse.<Page<ContactResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(contactResponses)
                .build();
    }

    //Not: getById() *********************************************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{contactId}") // http://localhost:8080/contact-messages/EN/get/id
    public InternalApiResponse<ContactResponse> getContact(@PathVariable("language") Language language,
                                                           @PathVariable("contactId") Long id
    ) {
        log.debug("[{}][getContact] -> request contactId: {}", this.getClass().getSimpleName(), id);
        ContactResponse contactResponse = contactService.getByContactId(language, id);

        log.debug("[{}][getContact] -> response: {}", this.getClass().getSimpleName(), contactResponse);
        return InternalApiResponse.<ContactResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(contactResponse)
                .build();
    }

    //Not: add() ****************************************************************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/add") // http://localhost:8080/contact-messages/EN/add
    public InternalApiResponse<ContactResponse> addContact(@PathVariable("language") Language language,
                                                           @RequestBody @Valid ContactRequest contactRequest
    ) {
        log.debug("[{}][createContact] -> request: {}", this.getClass().getSimpleName(), contactRequest);
        Contact contact = contactService.add(language, contactRequest);

        ContactResponse contactResponse = contactMapper.toResponse(contact);
        log.debug("[{}][createContact] -> response: {}", this.getClass().getSimpleName(), contactResponse);
        return InternalApiResponse.<ContactResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.CONTACT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(contactResponse)
                .build();
    }
}
