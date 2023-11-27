package com.prettier.controller;

import com.prettier.payload.request.concretes.ContactRequest;
import com.prettier.payload.response.ContactResponse;
import com.prettier.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact-messages")
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/add")
    public ResponseEntity<ContactResponse> add(@RequestBody @Valid ContactRequest contactRequest) {

        return ResponseEntity.ok((ContactResponse) contactService.add(contactRequest));
    }

}
