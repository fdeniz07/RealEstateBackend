package com.prettier.service;

import com.prettier.entity.concretes.Contact;
import com.prettier.payload.mapper.ContactMapper;
import com.prettier.payload.request.concretes.ContactRequest;
import com.prettier.payload.response.concretes.ContactResponse;
import com.prettier.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    ContactMapper contactMapper;
    private final ModelMapper modelMapper;

    public ContactResponse add(ContactRequest contactRequest) {

        Contact contact = contactMapper.toContact(contactRequest);

        return contactMapper.toResponse(contactRepository.save(contact));
    }


    public Page<ContactResponse> getAll(int page, int size, String sort, String type) {

        Pageable pageable =PageRequest.of(page,size, Sort.by(sort).ascending());
        if (Objects.equals(type,"desc")){
            pageable = PageRequest.of(page,size, Sort.by(sort).descending());
        }

        return contactRepository.findAll(pageable).map(contactMapper::toResponse);
    }
}
