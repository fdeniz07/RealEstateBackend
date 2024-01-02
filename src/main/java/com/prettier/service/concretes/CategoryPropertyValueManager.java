package com.prettier.service.concretes;

import com.prettier.repository.CategoryPropertyValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryPropertyValueManager {

    private final CategoryPropertyValueRepository categoryPropertyValueRepository;

}
