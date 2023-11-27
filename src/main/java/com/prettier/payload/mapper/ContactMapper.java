package com.prettier.payload.mapper;

import com.prettier.entity.concretes.Contact;
import com.prettier.payload.request.concretes.ContactRequest;
import com.prettier.payload.response.ContactResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Contact toContact(ContactRequest contactRequest) {

        return modelMapper.map(contactRequest, Contact.class);
    }

    public ContactResponse toResponse(Contact contact) {

        return modelMapper.map(contact, ContactResponse.class);
    }
}
