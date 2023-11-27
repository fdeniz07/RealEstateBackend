package com.prettier.service;


import com.prettier.repository.AdvertTypeRepository;
import com.prettier.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class AdvertTypeService implements Serializable {

    private final AdvertTypeRepository advertTypeRepository;
}
