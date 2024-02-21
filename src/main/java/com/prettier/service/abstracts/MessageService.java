package com.prettier.service.abstracts;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.Message;
import com.prettier.payload.request.concretes.*;
import com.prettier.payload.response.concretes.AdvertResponse;
import com.prettier.payload.response.concretes.MessageResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface MessageService extends Serializable {

    Page<MessageResponse> getListInbox(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getListSendBox(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getReadList(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getUnReadList(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getListDraft(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getListTrash(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getListImportant(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getListSpam(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getListBySenderId(Language language, Long id, int page, int size, String sort, String type);

    MessageResponse getMessageById(Language language, Long id);

    MessageResponse send(Language language, NewMessageRequest messageRequest);

    MessageResponse update(Language language, MessageUpdateRequest messageUpdateRequest, Long id);

    MessageResponse softDelete(Language language, Long id);

}
