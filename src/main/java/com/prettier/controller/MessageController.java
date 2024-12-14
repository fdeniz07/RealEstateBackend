package com.prettier.controller;

import com.prettier.payloads.mapper.MessageMapper;
import com.prettier.payloads.request.concretes.MessageUpdateRequest;
import com.prettier.payloads.request.concretes.NewMessageRequest;
import com.prettier.payloads.response.concretes.MessageResponse;
import com.prettier.service.abstracts.MessageService;
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
    @GetMapping("/{language}/getListInbox")
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
    @GetMapping("/{language}/getListSendBox")
    public InternalApiResponse<Page<MessageResponse>> getListSendBox(@PathVariable("language") Language language,
                                                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                                                     @RequestParam(value = "size", defaultValue = "50") int size,
                                                                     @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                                                     @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        log.debug("[{}][getListSendBox]", this.getClass().getSimpleName());
        Page<MessageResponse> messageResponses = messageService.getListSendBox(language, page, size, sort, type);

        log.debug("[{}][getListSendBox] -> response: {}", this.getClass().getSimpleName(), messageResponses);
        return InternalApiResponse.<Page<MessageResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponses)
                .build();
    }

    //Not: getReadList() ***********************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getReadList")
    public InternalApiResponse<Page<MessageResponse>> getReadList(@PathVariable("language") Language language,
                                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "30") int size,
                                                                  @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                                                  @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        log.debug("[{}][getReadList]", this.getClass().getSimpleName());
        Page<MessageResponse> messageResponses = messageService.getReadList(language, page, size, sort, type);

        log.debug("[{}][getReadList] -> response: {}", this.getClass().getSimpleName(), messageResponses);
        return InternalApiResponse.<Page<MessageResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponses)
                .build();
    }

    //Not: getUnReadList() *********************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getUnReadList")
    public InternalApiResponse<Page<MessageResponse>> getUnReadList(@PathVariable("language") Language language,
                                                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(value = "size", defaultValue = "30") int size,
                                                                    @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                                                    @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        log.debug("[{}][getUnReadList]", this.getClass().getSimpleName());
        Page<MessageResponse> messageResponses = messageService.getUnReadList(language, page, size, sort, type);

        log.debug("[{}][getUnReadList] -> response: {}", this.getClass().getSimpleName(), messageResponses);
        return InternalApiResponse.<Page<MessageResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponses)
                .build();
    }

    //Not: getListDraft() **********************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getListDraft")
    public InternalApiResponse<Page<MessageResponse>> getListDraft(@PathVariable("language") Language language,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "20") int size,
                                                                   @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                                                   @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        log.debug("[{}][getListDraft]", this.getClass().getSimpleName());
        Page<MessageResponse> messageResponses = messageService.getListDraft(language, page, size, sort, type);

        log.debug("[{}][getListDraft] -> response: {}", this.getClass().getSimpleName(), messageResponses);
        return InternalApiResponse.<Page<MessageResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponses)
                .build();
    }

    //Not: getListTrash() **********************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getListTrash")
    public InternalApiResponse<Page<MessageResponse>> getListTrash(@PathVariable("language") Language language,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "20") int size,
                                                                   @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                                                   @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        log.debug("[{}][getListTrash]", this.getClass().getSimpleName());
        Page<MessageResponse> messageResponses = messageService.getListTrash(language, page, size, sort, type);

        log.debug("[{}][getListTrash] -> response: {}", this.getClass().getSimpleName(), messageResponses);
        return InternalApiResponse.<Page<MessageResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponses)
                .build();
    }

    //Not: getListImportant() ******************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getListImportant")
    public InternalApiResponse<Page<MessageResponse>> getListImportant(@PathVariable("language") Language language,
                                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "20") int size,
                                                                       @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                                                       @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        log.debug("[{}][getListImportant]", this.getClass().getSimpleName());
        Page<MessageResponse> messageResponses = messageService.getListImportant(language, page, size, sort, type);

        log.debug("[{}][getListImportant] -> response: {}", this.getClass().getSimpleName(), messageResponses);
        return InternalApiResponse.<Page<MessageResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponses)
                .build();
    }

    //Not: getListSpam() ***********************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getListSpam")
    public InternalApiResponse<Page<MessageResponse>> getListSpam(@PathVariable("language") Language language,
                                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "20") int size,
                                                                  @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                                                  @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        log.debug("[{}][getListSpam]", this.getClass().getSimpleName());
        Page<MessageResponse> messageResponses = messageService.getListSpam(language, page, size, sort, type);

        log.debug("[{}][getListSpam] -> response: {}", this.getClass().getSimpleName(), messageResponses);
        return InternalApiResponse.<Page<MessageResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponses)
                .build();
    }

    //Not: getListBySenderId() ******************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getMessages/{senderId}")
    public InternalApiResponse<Page<MessageResponse>> getListBySenderId(@PathVariable("language") Language language,
                                                                        @PathVariable("senderId") Long id,
                                                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                                                        @RequestParam(value = "size", defaultValue = "20") int size,
                                                                        @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                                                        @RequestParam(value = "type", defaultValue = "desc") String type
    ) {

        log.debug("[{}][getListBySenderId]", this.getClass().getSimpleName());
        Page<MessageResponse> messageResponses = messageService.getListBySenderId(language, id, page, size, sort, type);

        log.debug("[{}][getListBySenderId] -> response: {}", this.getClass().getSimpleName(), messageResponses);
        return InternalApiResponse.<Page<MessageResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponses)
                .build();
    }

    //Not: getMessageById() ********************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/{id}")
    public InternalApiResponse<MessageResponse> getMessageById(@PathVariable("language") Language language,
                                                               @PathVariable("id") Long id
    ) {

        log.debug("[{}][getMessageById]", this.getClass().getSimpleName());
        MessageResponse messageResponse = messageService.getMessageById(language, id);

        log.debug("[{}][getMessageById] -> response: {}", this.getClass().getSimpleName(), messageResponse);
        return InternalApiResponse.<MessageResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponse)
                .build();
    }

    //Not: send() *******************************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{language}/sendMessage")
    public InternalApiResponse<MessageResponse> sendMessage(@PathVariable("language") Language language,
                                                            @RequestBody @Valid NewMessageRequest request
    ) {

        log.debug("[{}][sendMessage] -> request: {}", this.getClass().getSimpleName(), request);
        MessageResponse messageResponse = messageService.send(language, request);

        log.debug("[{}][sendMessage] -> response: {}", this.getClass().getSimpleName(), messageResponse);
        return InternalApiResponse.<MessageResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.MESSAGE_SUCCESSFULLY_SENT))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponse)
                .build();
    }

    //Not: update() ****************************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{messageId}")
    public InternalApiResponse<MessageResponse> updateMessage(@PathVariable("language") Language language,
                                                              @PathVariable("messageId") Long id,
                                                              @RequestBody @Valid MessageUpdateRequest request
    ) {
        log.debug("[{}][updateMessage] -> request: {} {}", this.getClass().getSimpleName(), id, request);
        MessageResponse messageResponse = messageService.update(language, request, id);

        log.debug("[{}][updateMessage] -> response: {}", this.getClass().getSimpleName(), messageResponse);
        return InternalApiResponse.<MessageResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.MESSAGE_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponse)
                .build();
    }

    //Not: delete() ****************************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/delete/{messageId}")
    public InternalApiResponse<MessageResponse> deleteMessage(@PathVariable("language") Language language,
                                                              @PathVariable("countryId") Long id
    ) {
        log.debug("[{}][deleteMessage] -> request countryId: {}", this.getClass().getSimpleName(), id);
        MessageResponse messageResponse = messageService.softDelete(language, id);

        log.debug("[{}][deleteMessage] -> response: {}", this.getClass().getSimpleName(), messageResponse);
        return InternalApiResponse.<MessageResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.MESSAGE_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(messageResponse)
                .build();
    }
}
