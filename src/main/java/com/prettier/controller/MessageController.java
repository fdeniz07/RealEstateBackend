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

/**
 * REST controller for managing user messages in the system.
 * Handles endpoints for creating, retrieving, updating, and deleting user messages.
 *
 * <p>This controller provides comprehensive functionality for managing messages
 * between users, including inbox, outbox, drafts, and other message categories.</p>
 *
 * @author Fatih Deniz
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Message", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/messages")
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;


    //Not: getListInbox() **********************************************************************************************************
    /**
     * Retrieves all inbox messages with pagination support.
     *
     * @param language The language for the response content
     * @param page The page number (zero-based) to retrieve
     * @param size The size of the page to retrieve
     * @param sort The field to sort by
     * @param type The sort direction (desc by default)
     * @return An internal API response containing a page of inbox message responses
     */
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
    /**
     * Retrieves all sent messages with pagination support.
     *
     * @param language The language for the response content
     * @param page The page number (zero-based) to retrieve
     * @param size The size of the page to retrieve
     * @param sort The field to sort by
     * @param type The sort direction (desc by default)
     * @return An internal API response containing a page of sent message responses
     */
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
    /**
     * Retrieves all read messages with pagination support.
     *
     * @param language The language for the response content
     * @param page The page number (zero-based) to retrieve
     * @param size The size of the page to retrieve
     * @param sort The field to sort by
     * @param type The sort direction (desc by default)
     * @return An internal API response containing a page of read message responses
     */
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
    /**
     * Retrieves all unread messages with pagination support.
     *
     * @param language The language for the response content
     * @param page The page number (zero-based) to retrieve
     * @param size The size of the page to retrieve
     * @param sort The field to sort by
     * @param type The sort direction (desc by default)
     * @return An internal API response containing a page of unread message responses
     */
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
    /**
     * Retrieves all draft messages with pagination support.
     *
     * @param language The language for the response content
     * @param page The page number (zero-based) to retrieve
     * @param size The size of the page to retrieve
     * @param sort The field to sort by
     * @param type The sort direction (desc by default)
     * @return An internal API response containing a page of draft message responses
     */
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
    /**
     * Retrieves all trash messages with pagination support.
     *
     * @param language The language for the response content
     * @param page The page number (zero-based) to retrieve
     * @param size The size of the page to retrieve
     * @param sort The field to sort by
     * @param type The sort direction (desc by default)
     * @return An internal API response containing a page of trash message responses
     */
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
    /**
     * Retrieves all important messages with pagination support.
     *
     * @param language The language for the response content
     * @param page The page number (zero-based) to retrieve
     * @param size The size of the page to retrieve
     * @param sort The field to sort by
     * @param type The sort direction (desc by default)
     * @return An internal API response containing a page of important message responses
     */
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
    /**
     * Retrieves all spam messages with pagination support.
     *
     * @param language The language for the response content
     * @param page The page number (zero-based) to retrieve
     * @param size The size of the page to retrieve
     * @param sort The field to sort by
     * @param type The sort direction (desc by default)
     * @return An internal API response containing a page of spam message responses
     */
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
    /**
     * Retrieves all messages from a specific sender with pagination support.
     *
     * @param language The language for the response content
     * @param id The ID of the sender
     * @param page The page number (zero-based) to retrieve
     * @param size The size of the page to retrieve
     * @param sort The field to sort by
     * @param type The sort direction (desc by default)
     * @return An internal API response containing a page of messages from the specified sender
     */
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
    /**
     * Retrieves a specific message by its ID.
     *
     * @param language The language for the response content
     * @param id The ID of the message to retrieve
     * @return An internal API response containing the message response
     */
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
    /**
     * Sends a new message in the system.
     *
     * @param language The language for the response content
     * @param request The message data to send
     * @return An internal API response containing the sent message
     */
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
    /**
     * Updates an existing message in the system.
     *
     * @param language The language for the response content
     * @param id The ID of the message to update
     * @param request The updated message data
     * @return An internal API response containing the updated message
     */
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
    /**
     * Soft deletes a message from the system.
     *
     * @param language The language for the response content
     * @param id The ID of the message to delete
     * @return An internal API response containing the deleted message
     */
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
