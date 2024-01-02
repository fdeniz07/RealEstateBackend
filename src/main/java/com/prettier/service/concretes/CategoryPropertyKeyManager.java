package com.prettier.service.concretes;

import com.prettier.repository.CategoryPropertyKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryPropertyKeyManager {

    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;

}
