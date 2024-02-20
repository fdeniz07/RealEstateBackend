package com.prettier.controller;

import com.prettier.entity.concretes.Country;
import com.prettier.payload.mapper.CountryMapper;
import com.prettier.payload.mapper.MessageMapper;
import com.prettier.payload.request.concretes.CountryRequest;
import com.prettier.payload.request.concretes.CountryUpdateRequest;
import com.prettier.payload.response.concretes.CountryResponse;
import com.prettier.payload.response.concretes.MessageResponse;
import com.prettier.service.abstracts.MessageService;
import com.prettier.service.concretes.CountryManager;
import com.prettier.service.concretes.MessageManager;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.globalExceptionHandling.FriendlyMessage;
import com.prettier.shared.exception.globalExceptionHandling.InternalApiResponse;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.io.StringReader;

@RestController
@RequiredArgsConstructor
@Tag(name = "Message", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/messages")
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;


    //Not: getListInbox() **********************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{language}/getListInbox")
    public InternalApiResponse<Page<MessageResponse>> getListInbox(@PathVariable("language") Language language,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "50") int size,
                                                                   @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                                                   @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        log.debug("[{}][getListInbox]", this.getClass().getSimpleName());
        Page<MessageResponse> messageResponses = messageService.getListInbox(language, page, size, sort, type);

        log.debug("[{}][getListInbox] -> response: {}", this.getClass().getSimpleName(), messageResponses);
        return InternalApiResponse.<Page<MessageResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponses)
                .build();
    }

    //Not: getListSendbox() ********************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{language}/getListSendbox")
    public InternalApiResponse<Page<MessageResponse>> getListSendbox(@PathVariable("languege") Language language,
                                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "size", defaultValue = "0") int size,
                                                                     @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                                                     @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        log.debug("[{}][getListSendbox]", this.getClass().getSimpleName());
        Page<MessageResponse> messageResponses = messageService.getListSendbox(language, page, size, sort, type);

        log.debug("[{}][getListInbox] -> response: {}", this.getClass().getSimpleName(), messageResponses);
        return InternalApiResponse.<Page<MessageResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponses)
                .build();
    }

    //Not: getReadList() ***********************************************************************************************************


    //Not: getUnReadList() *********************************************************************************************************


    //Not: getListDraft() **********************************************************************************************************


    //Not: getListTrash() **********************************************************************************************************


    //Not: getListImportant() ******************************************************************************************************


    //Not: getListSpam() ***********************************************************************************************************


    //Not: getBySenderId() *********************************************************************************************************


    //Not: getMessageId() **********************************************************************************************************


    //Not: add() *******************************************************************************************************************


    //Not: update() ****************************************************************************************************************


    //Not: delete() ****************************************************************************************************************


}
