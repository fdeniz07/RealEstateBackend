package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.Contact;
import com.prettier.payloads.request.concretes.ContactRequest;
import com.prettier.payloads.request.concretes.ContactUpdateRequest;
import com.prettier.payloads.response.concretes.ContactResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    Contact toContact(ContactRequest contactRequest);

    ContactResponse toResponse(Contact contact);

    Contact toUpdatedContact(ContactUpdateRequest contactUpdateRequest, Contact existingContact);
}
