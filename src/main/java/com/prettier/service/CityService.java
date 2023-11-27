package com.prettier.service;

import com.prettier.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class CityService implements Serializable {

    private final CityRepository cityRepository;
}
