package com.prettier.service.concretes;


import com.prettier.repository.AdvertTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class AdvertTypeManager implements Serializable {

    private final AdvertTypeRepository advertTypeRepository;
}
