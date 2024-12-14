package com.prettier.service.concretes;

import com.prettier.entity.concretes.Message;
import com.prettier.entity.concretes.User;
import com.prettier.payloads.mapper.MessageMapper;
import com.prettier.payloads.request.concretes.MessageUpdateRequest;
import com.prettier.payloads.request.concretes.NewMessageRequest;
import com.prettier.payloads.response.concretes.MessageResponse;
import com.prettier.repository.MessageRepository;
import com.prettier.service.abstracts.MessageService;
import com.prettier.service.abstracts.UserService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.messages.MessageNotFoundException;
import com.prettier.shared.exception.exceptions.messages.MessageNotSentException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageManager implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final MessageMapper messageMapper;


    //Not: getListInbox() **********************************************************************************************************
    @Override
    public Page<MessageResponse> getListInbox(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getListInbox]", this.getClass().getSimpleName());

        Pageable pageable;
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();

            Page<Message> messagePage = messageRepository.findAllByReceiverEquals(email, pageable);

            List<MessageResponse> messages = messagePage.getContent().stream()
                    .map(messageMapper::toResponse)
                    .collect(Collectors.toList());

            if (messages.isEmpty()) {
                throw new MessageNotFoundException(language, FriendlyMessageCodes.MESSAGE_NOT_FOUND_EXCEPTION, "Messages not found");
            }
            log.debug("[{}][getListInbox] -> response: {}", this.getClass().getSimpleName(), messages);

            return new PageImpl<>(messages, pageable, messagePage.getTotalElements());
        }
        return null;
    }

    //Not: getListSendBox() ********************************************************************************************************
    @Override
    public Page<MessageResponse> getListSendBox(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getListInbox]", this.getClass().getSimpleName());

        Pageable pageable;
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();

            Page<Message> messagePage = messageRepository.findAllBySenderEquals(email, pageable);

            List<MessageResponse> messages = messagePage.getContent().stream()
                    .map(messageMapper::toResponse)
                    .collect(Collectors.toList());

            if (messages.isEmpty()) {
                throw new MessageNotFoundException(language, FriendlyMessageCodes.MESSAGE_NOT_FOUND_EXCEPTION, "Messages not found");
            }
            log.debug("[{}][getListInbox] -> response: {}", this.getClass().getSimpleName(), messages);

            return new PageImpl<>(messages, pageable, messagePage.getTotalElements());
        }
        return null;
    }

    //Not: getReadList() ***********************************************************************************************************
    @Override
    public Page<MessageResponse> getReadList(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getReadList]", this.getClass().getSimpleName());

        Pageable pageable;
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();

            Page<Message> messagePage = messageRepository.findAllByReceiverEqualsAndReadTrue(email, pageable);

            List<MessageResponse> messages = messagePage.getContent().stream()
                    .map(messageMapper::toResponse)
                    .collect(Collectors.toList());

            if (messages.isEmpty()) {
                throw new MessageNotFoundException(language, FriendlyMessageCodes.MESSAGE_NOT_FOUND_EXCEPTION, "Messages not found");
            }
            log.debug("[{}][getReadList] -> response: {}", this.getClass().getSimpleName(), messages);

            return new PageImpl<>(messages, pageable, messagePage.getTotalElements());
        }
        return null;
    }

    //Not: getUnReadList() *********************************************************************************************************
    @Override
    public Page<MessageResponse> getUnReadList(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getUnReadList]", this.getClass().getSimpleName());

        Pageable pageable;
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();

            Page<Message> messagePage = messageRepository.findAllByReceiverEqualsAndReadFalse(email, pageable);

            List<MessageResponse> messages = messagePage.getContent().stream()
                    .map(messageMapper::toResponse)
                    .collect(Collectors.toList());

            if (messages.isEmpty()) {
                throw new MessageNotFoundException(language, FriendlyMessageCodes.MESSAGE_NOT_FOUND_EXCEPTION, "Messages not found");
            }
            log.debug("[{}][getUnReadList] -> response: {}", this.getClass().getSimpleName(), messages);

            return new PageImpl<>(messages, pageable, messagePage.getTotalElements());
        }
        return null;
    }

    //Not: getListDraft() **********************************************************************************************************
    @Override
    public Page<MessageResponse> getListDraft(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getListDraft]", this.getClass().getSimpleName());

        Pageable pageable;
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();

            Page<Message> messagePage = messageRepository.findAllByReceiverEqualsAndDraftTrue(email, pageable);

            List<MessageResponse> messages = messagePage.getContent().stream()
                    .map(messageMapper::toResponse)
                    .collect(Collectors.toList());

            if (messages.isEmpty()) {
                throw new MessageNotFoundException(language, FriendlyMessageCodes.MESSAGE_NOT_FOUND_EXCEPTION, "Messages not found");
            }
            log.debug("[{}][getListDraft] -> response: {}", this.getClass().getSimpleName(), messages);

            return new PageImpl<>(messages, pageable, messagePage.getTotalElements());
        }
        return null;
    }

    //Not: getListTrash() **********************************************************************************************************
    @Override
    public Page<MessageResponse> getListTrash(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getListTrash]", this.getClass().getSimpleName());

        Pageable pageable;
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();

            Page<Message> messagePage = messageRepository.findAllByReceiverEqualsAndTrashTrue(email, pageable);

            List<MessageResponse> messages = messagePage.getContent().stream()
                    .map(messageMapper::toResponse)
                    .collect(Collectors.toList());

            if (messages.isEmpty()) {
                throw new MessageNotFoundException(language, FriendlyMessageCodes.MESSAGE_NOT_FOUND_EXCEPTION, "Messages not found");
            }
            log.debug("[{}][getListTrash] -> response: {}", this.getClass().getSimpleName(), messages);

            return new PageImpl<>(messages, pageable, messagePage.getTotalElements());
        }
        return null;
    }

    //Not: getListImportant() ******************************************************************************************************
    @Override
    public Page<MessageResponse> getListImportant(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getListImportant]", this.getClass().getSimpleName());

        Pageable pageable;
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();

            Page<Message> messagePage = messageRepository.findAllByReceiverEqualsAndImportantTrue(email, pageable);

            List<MessageResponse> messages = messagePage.getContent().stream()
                    .map(messageMapper::toResponse)
                    .collect(Collectors.toList());

            if (messages.isEmpty()) {
                throw new MessageNotFoundException(language, FriendlyMessageCodes.MESSAGE_NOT_FOUND_EXCEPTION, "Messages not found");
            }
            log.debug("[{}][getListImportant] -> response: {}", this.getClass().getSimpleName(), messages);

            return new PageImpl<>(messages, pageable, messagePage.getTotalElements());
        }
        return null;
    }

    //Not: getListSpam() ***********************************************************************************************************
    @Override
    public Page<MessageResponse> getListSpam(Language language, int page, int size, String sort, String type) {

        //!!! TODO: For spams must be a spamList create !!!

        log.debug("[{}][getListSpam]", this.getClass().getSimpleName());

        Pageable pageable;
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();

            Page<Message> messagePage = messageRepository.findAllByReceiverEqualsAndSpamTrue(email, pageable);

            List<MessageResponse> messages = messagePage.getContent().stream()
                    .map(messageMapper::toResponse)
                    .collect(Collectors.toList());

            if (messages.isEmpty()) {
                throw new MessageNotFoundException(language, FriendlyMessageCodes.MESSAGE_NOT_FOUND_EXCEPTION, "Messages not found");
            }
            log.debug("[{}][getListSpam] -> response: {}", this.getClass().getSimpleName(), messages);

            return new PageImpl<>(messages, pageable, messagePage.getTotalElements());
        }
        return null;
    }

    //Not: getListBySenderId() ******************************************************************************************************
    @Override
    public Page<MessageResponse> getListBySenderId(Language language, Long id, int page, int size, String sort, String type)
     {

        log.debug("[{}][getListBySenderId]", this.getClass().getSimpleName());

        Pageable pageable;
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();

            String senderEmail = userService.getUserMailById(id);

            Page<Message> messagePage = messageRepository.findAllBySender(email, senderEmail, pageable);

            List<MessageResponse> messages = messagePage.getContent().stream()
                    .map(messageMapper::toResponse)
                    .collect(Collectors.toList());

            if (messages.isEmpty()) {
                throw new MessageNotFoundException(language, FriendlyMessageCodes.MESSAGE_NOT_FOUND_EXCEPTION, "Messages not found");
            }
            log.debug("[{}][getListBySenderId] -> response: {}", this.getClass().getSimpleName(), messages);

            return new PageImpl<>(messages, pageable, messagePage.getTotalElements());
        }
        return null;
    }

    //Not: getMessageById() **********************************************************************************************************
    @Override
    public MessageResponse getMessageById(Language language, Long id) {

        log.debug("[{}][getListBySenderId]", this.getClass().getSimpleName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

            String email = userDetails.getUsername();

            Message message = messageRepository.findById(id)
                    .orElseThrow(() -> new MessageNotFoundException(language, FriendlyMessageCodes.MESSAGE_NOT_FOUND_EXCEPTION, "Messages not found"));

            MessageResponse response = messageMapper.toResponse(message);

            log.debug("[{}][getListBySenderId] -> response: {}", this.getClass().getSimpleName(), response);

            return response;
        }
        return null;
    }

    //Not: send() *******************************************************************************************************************
    @Override
    public MessageResponse send(Language language, NewMessageRequest messageRequest) {

        log.debug("[{}][sendMessage]", this.getClass().getSimpleName());

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {

                String loginUser = userDetails.getUsername();
                User sender = userService.getUserByEmail(language, loginUser);

                User receiver = userService.getUserByEmail(language, messageRequest.getReceiverMail());
                Message message = messageMapper.toMessage(messageRequest);
                message.setReceiver(receiver);
                message.setSender(sender);
                MessageResponse sentMessage = messageMapper.toResponse(messageRepository.save(message));

                log.debug("[{}][sendMessage] -> response: {}", this.getClass().getSimpleName(), sentMessage);
                return sentMessage;
            }
        } catch (Exception e) {
            throw new MessageNotSentException(language, FriendlyMessageCodes.MESSAGE_NOT_SENT_EXCEPTION, "message request: " + messageRequest.toString());
        }

        return null;
    }

    //Not: update() ****************************************************************************************************************
    @Override
    public MessageResponse update(Language language, MessageUpdateRequest messageUpdateRequest, Long id) {
        return null;
    }

    //Not: delete() ****************************************************************************************************************
    @Override
    public MessageResponse softDelete(Language language, Long id) {
        return null;
    }
}
