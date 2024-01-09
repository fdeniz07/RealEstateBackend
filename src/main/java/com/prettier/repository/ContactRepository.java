package com.prettier.repository;

import com.prettier.entity.concretes.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

    boolean existsByEmail(String contactEmail);
}
