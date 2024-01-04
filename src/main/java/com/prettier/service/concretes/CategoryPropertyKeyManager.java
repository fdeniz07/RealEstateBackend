package com.prettier.service.concretes;

import com.prettier.repository.CategoryPropertyKeyRepository;
import com.prettier.service.abstracts.CategoryPropertyKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryPropertyKeyManager implements CategoryPropertyKeyService {

    private CategoryPropertyKeyRepository categoryPropertyKeyRepository;

}
