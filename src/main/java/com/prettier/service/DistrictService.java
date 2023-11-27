package com.prettier.service;

import com.prettier.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class DistrictService implements Serializable {

    private final DistrictRepository districtRepository;
}
