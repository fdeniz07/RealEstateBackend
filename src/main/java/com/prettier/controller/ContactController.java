package com.prettier.controller;

import com.prettier.payload.request.concretes.ContactRequest;
import com.prettier.payload.response.concretes.ContactResponse;
import com.prettier.service.concretes.ContactService;
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

    @PostMapping("/add") //http://localhost:8080/contact-messages/add
    public ResponseEntity<ContactResponse> add(@RequestBody @Valid ContactRequest contactRequest) {

        return ResponseEntity.ok((ContactResponse) contactService.add(contactRequest));
    }


    @GetMapping("/getAll") //http://localhost:8080/contact-messages/getAll
    public Page<ContactResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "1") int size,
            @RequestParam(value = "sort", defaultValue = "email") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type) {

        return contactService.getAll(page, size, sort, type);
    }

}
