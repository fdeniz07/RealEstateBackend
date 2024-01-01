package com.prettier.service.abstracts;

import com.prettier.entity.concretes.Contact;
import com.prettier.payload.request.concretes.ContactRequest;
import com.prettier.payload.request.concretes.ContactUpdateRequest;
import com.prettier.payload.response.concretes.ContactResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface ContactService extends Serializable {

    Page<ContactResponse> getContacts(Language language, int page, int size, String sort, String type);

    ContactResponse getByContactId(Language language, Long id);

    Contact add(Language language, ContactRequest cityRequest);

    Contact update(Language language, ContactUpdateRequest contactUpdateRequest, Long id);

    ContactResponse softDelete(Language language, Long id);
}
