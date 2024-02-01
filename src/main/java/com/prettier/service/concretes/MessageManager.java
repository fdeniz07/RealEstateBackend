package com.prettier.service.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.payload.request.concretes.AdvertRequest;
import com.prettier.payload.request.concretes.AdvertUpdateRequest;
import com.prettier.payload.response.concretes.MessageResponse;
import com.prettier.repository.MessageRepository;
import com.prettier.service.abstracts.MessageService;
import com.prettier.service.abstracts.UserService;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageManager implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;


    //Not: getListInbox() **********************************************************************************************************
    @Override
    public Page<MessageResponse> getListInbox(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: getListSendbox() ********************************************************************************************************
    @Override
    public Page<MessageResponse> getListSendbox(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: getReadList() ***********************************************************************************************************
    @Override
    public Page<MessageResponse> getReadList(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: getUnReadList() *********************************************************************************************************
    @Override
    public Page<MessageResponse> getUnReadList(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: getListDraft() **********************************************************************************************************
    @Override
    public Page<MessageResponse> getListDraft(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: getListTrash() **********************************************************************************************************
    @Override
    public Page<MessageResponse> getListTrash(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: getListImportant() ******************************************************************************************************
    @Override
    public Page<MessageResponse> getListImportant(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: getListSpam() ***********************************************************************************************************
    @Override
    public Page<MessageResponse> getListSpam(Language language, int page, int size, String sort, String type) {
        return null;
    }

    //Not: getBySenderId() *********************************************************************************************************
    @Override
    public Page<MessageResponse> getBySenderId(Language language, Long id) {
        return null;
    }

    //Not: getMessageId() **********************************************************************************************************
    @Override
    public MessageResponse getMessageId(Language language, Long id) {
        return null;
    }

    //Not: add() *******************************************************************************************************************
    @Override
    public Advert add(Language language, AdvertRequest advertRequest) {
        return null;
    }

    //Not: update() ****************************************************************************************************************
    @Override
    public Advert update(Language language, AdvertUpdateRequest advertUpdateRequest, Long id) {
        return null;
    }

    //Not: delete() ****************************************************************************************************************
    @Override
    public Advert delete(Language language, Long id) {
        return null;
    }
}
