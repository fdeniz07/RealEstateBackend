package com.prettier.service;

import com.prettier.entity.concretes.Contact;
import com.prettier.payload.mapper.ContactMapper;
import com.prettier.payload.request.concretes.ContactRequest;
import com.prettier.payload.response.ContactResponse;
import com.prettier.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    ContactMapper contactMapper;

    public ContactResponse add(ContactRequest contactRequest) {

        Contact contact = contactMapper.toContact(contactRequest);

        return contactMapper.toResponse(contactRepository.save(contact));
    }
}
