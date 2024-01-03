package com.prettier.service.concretes;

import com.prettier.repository.CategoryPropertyValueRepository;
import com.prettier.service.abstracts.CategoryPropertyValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryPropertyValueManager implements CategoryPropertyValueService {

    private final CategoryPropertyValueRepository categoryPropertyValueRepository;

}
