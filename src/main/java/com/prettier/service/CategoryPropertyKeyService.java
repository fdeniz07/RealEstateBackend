package com.prettier.service;

import com.prettier.repository.CategoryPropertyKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryPropertyKeyService {

    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;

}
