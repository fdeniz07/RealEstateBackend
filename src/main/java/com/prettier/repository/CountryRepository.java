package com.prettier.repository;

import com.prettier.entity.concretes.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

    boolean existsByName(String countryName);
}
