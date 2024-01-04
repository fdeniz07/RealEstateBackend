package com.prettier.payload.mapper;

import com.prettier.entity.concretes.Contact;
import com.prettier.payload.request.concretes.ContactRequest;
import com.prettier.payload.request.concretes.ContactUpdateRequest;
import com.prettier.payload.response.concretes.ContactResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    Contact toContact(ContactRequest contactRequest);

    ContactResponse toResponse(Contact contact);

    Contact toUpdatedContact(ContactUpdateRequest contactUpdateRequest, Contact existingContact);
    
    
//    @Autowired
//    private ModelMapper modelMapper;
//
//    public Contact toContact(ContactRequest contactRequest) {
//
//        return modelMapper.map(contactRequest, Contact.class);
//    }
//
//    public ContactResponse toResponse(Contact contact) {
//
//        return modelMapper.map(contact, ContactResponse.class);
//    }
}
