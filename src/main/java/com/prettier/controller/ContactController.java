package com.prettier.controller;

import com.prettier.entity.concretes.Contact;
import com.prettier.payload.mapper.ContactMapper;
import com.prettier.payload.request.concretes.ContactRequest;
import com.prettier.payload.response.FriendlyMessage;
import com.prettier.payload.response.InternalApiResponse;
import com.prettier.payload.response.concretes.ContactResponse;
import com.prettier.payload.response.concretes.ContactResponse;
import com.prettier.service.abstracts.ContactService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact-messages")
@Slf4j
public class ContactController {

    private final ContactService contactService;
    private final ContactMapper contactMapper;

//    @GetMapping("/getAll") //http://localhost:8080/contact-messages/getAll
//    public Page<ContactResponse> getAll(
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "1") int size,
//            @RequestParam(value = "sort", defaultValue = "email") String sort,
//            @RequestParam(value = "type", defaultValue = "asc") String type) {
//
//        return contactService.getAll(page, size, sort, type);
//    }

    @GetMapping(value = "/{language}/contacts") // http://localhost:8080/cities/EN/cities
    public Page<ContactResponse> getCities(
            @PathVariable("language") Language language,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "email") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        return contactService.getContacts(language, page, size, sort, type);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{contactId}")
    public InternalApiResponse<ContactResponse> getContact(@PathVariable("language") Language language,
                                                           @PathVariable("contactId") Long id) {
        log.debug("[{}][getContact] -> request contactId: {}", this.getClass().getSimpleName(), id);
        ContactResponse contactResponse = contactService.getByContactId(language, id);

        log.debug("[{}][getContact] -> response: {}", this.getClass().getSimpleName(), contactResponse);
        return InternalApiResponse.<ContactResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(contactResponse)
                .build();
    }

//    @PostMapping("/add") //http://localhost:8080/contact-messages/add
//    public ResponseEntity<ContactResponse> add(@RequestBody @Valid ContactRequest contactRequest) {
//
//        return ResponseEntity.ok((ContactResponse) contactService.add(contactRequest));
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/add")
    public InternalApiResponse<ContactResponse> addContact(@PathVariable("language") Language language,
                                                     @RequestBody ContactRequest contactRequest) {
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
