package com.prettier.service.concretes;

import com.prettier.entity.concretes.Contact;
import com.prettier.payloads.mapper.ContactMapper;
import com.prettier.payloads.request.concretes.ContactRequest;
import com.prettier.payloads.request.concretes.ContactUpdateRequest;
import com.prettier.payloads.response.concretes.ContactResponse;
import com.prettier.repository.ContactRepository;
import com.prettier.service.abstracts.ContactService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.contacts.ContactNotCreatedException;
import com.prettier.shared.exception.exceptions.contacts.ContactNotFoundException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service implementation class that manages Contact-related operations.
 * This class handles CRUD operations and pagination for Contacts.
 * Implements the ContactService interface to provide contact management functionality.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContactManager implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    //Not: getAll() *********************************************************************************************************************************
    /**
     * Retrieves a paginated list of all contacts.
     *
     * @param language The language for error messages and localization
     * @param page The page number for pagination
     * @param size The number of items per page
     * @param sort The field to sort by
     * @param type The sort direction ("asc" or "desc")
     * @return A Page of ContactResponse containing all contacts
     * @throws ContactNotFoundException if no contacts are found
     */
    @Override
    public Page<ContactResponse> getContacts(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getContacts]", this.getClass().getSimpleName());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        List<ContactResponse> contacts = contactRepository.findAll()
                .stream()
                .map(contactMapper::toResponse)
                .collect(Collectors.toList());

        if (contacts.isEmpty()) {
            throw new ContactNotFoundException(language, FriendlyMessageCodes.CONTACT_NOT_FOUND_EXCEPTION, "Contacts not found");
        }
        log.debug("[{}][getContacts] -> response: {}", this.getClass().getSimpleName(), contacts);
        return contactRepository.findAll(pageable).map(contactMapper::toResponse);
    }

    //Not: getById() *********************************************************************************************************************************
    /**
     * Retrieves a specific contact by its ID.
     *
     * @param language The language for error messages and localization
     * @param id The ID of the contact to retrieve
     * @return ContactResponse containing the contact details
     * @throws ContactNotFoundException if the contact is not found
     */
    @Override
    public ContactResponse getByContactId(Language language, Long id) {

        log.debug("[{}][getContact] -> request contactId: {}", this.getClass().getSimpleName(), id);

        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException(language, FriendlyMessageCodes.CONTACT_NOT_FOUND_EXCEPTION, "Contact not found for contact id: " + id));

        log.debug("[{}][getContact] -> response: {}", this.getClass().getSimpleName(), contact);
        return contactMapper.toResponse(contact);
    }

    //Not: add() ****************************************************************************************************************************************
    /**
     * Creates a new contact.
     *
     * @param language The language for error messages and localization
     * @param contactRequest The contact creation request containing contact details
     * @return Contact entity containing the created contact details
     * @throws ContactNotCreatedException if the contact cannot be created
     */
    @Override
    public Contact add(Language language, ContactRequest contactRequest) {

        log.debug("[{}][createContact] -> request: {}", this.getClass().getSimpleName(), contactRequest);

        try {
            Contact contact = contactMapper.toContact(contactRequest);
            Contact response = contactRepository.save(contact);
            log.debug("[{}][createContact] -> response: {}", this.getClass().getSimpleName(), response);
            return response;
        } catch (Exception exception) {
            throw new ContactNotCreatedException(language, FriendlyMessageCodes.CONTACT_NOT_CREATED_EXCEPTION, "contact request: " + contactRequest.toString());
        }
    }

    //Not: update() *********************************************************************************************************************************
    /**
     * Updates an existing contact.
     * Currently not implemented.
     *
     * @param language The language for error messages and localization
     * @param contactUpdateRequest The contact update request containing updated details
     * @param id The ID of the contact to update
     * @return Contact entity containing the updated contact details
     * @throws ContactNotFoundException if the contact is not found
     */
    @Override
    public Contact update(Language language, ContactUpdateRequest contactUpdateRequest, Long id) {
        return null;
    }

    //Not: delete() *********************************************************************************************************************************
    /**
     * Performs a soft delete on a contact by marking it as deleted.
     * Currently not implemented.
     *
     * @param language The language for error messages and localization
     * @param id The ID of the contact to delete
     * @return ContactResponse containing the deleted contact details
     * @throws ContactNotFoundException if the contact is not found
     */
    @Override
    public ContactResponse softDelete(Language language, Long id) {
        return null;
    }
}
