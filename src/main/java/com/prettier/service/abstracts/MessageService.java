package com.prettier.service.abstracts;

import com.prettier.entity.concretes.Advert;
import com.prettier.payload.request.concretes.AdvertRequest;
import com.prettier.payload.request.concretes.AdvertUpdateRequest;
import com.prettier.payload.response.concretes.AdvertResponse;
import com.prettier.payload.response.concretes.MessageResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface MessageService extends Serializable {

    Page<MessageResponse> getListInbox(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getListSendbox(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getReadList(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getUnReadList(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getListDraft(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getListTrash(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getListImportant(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getListSpam(Language language, int page, int size, String sort, String type);

    Page<MessageResponse> getBySenderId(Language language, Long id);

    MessageResponse getMessageId(Language language, Long id);

    Advert add(Language language, AdvertRequest advertRequest);

    Advert update(Language language, AdvertUpdateRequest advertUpdateRequest, Long id);

    Advert delete(Language language, Long id);

}
