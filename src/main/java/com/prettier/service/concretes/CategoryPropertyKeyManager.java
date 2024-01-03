package com.prettier.service.concretes;

import com.prettier.repository.CategoryPropertyKeyRepository;
import com.prettier.service.abstracts.CategoryPropertyKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryPropertyKeyManager implements CategoryPropertyKeyService {

    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;

}
