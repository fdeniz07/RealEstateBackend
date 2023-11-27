package com.prettier.service;

import com.prettier.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class CountryService implements Serializable {

    private final CountryRepository countryRepository;
}
